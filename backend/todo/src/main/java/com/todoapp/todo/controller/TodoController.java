package com.todoapp.todo.controller;

import com.todoapp.todo.entity.Todo;
import com.todoapp.todo.service.TodoService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;

@RestController
@RequestMapping("/api/todos")
public class TodoController {
    private final TodoService todoService;

    // Constructor for dependency injection
    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    // Retrieves a paginated list of Todo items
    @GetMapping
    public ResponseEntity<Page<Todo>> getTodos(@RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "10") int size,
                                               @RequestParam(defaultValue = "title") String sortBy,
                                               @RequestParam(defaultValue = "asc") String direction) {
        try {
            Page<Todo> todos = todoService.getTodos(page, size, sortBy, direction);
            return ResponseEntity.ok(todos);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // Creates a new Todo item
    @PostMapping
    public ResponseEntity<Todo> createTodo(@Validated @RequestBody Todo todo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        try {
            Todo createdTodo = todoService.saveOrUpdate(todo);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTodo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // Updates an existing Todo item by its ID
    @PutMapping("/{id}")
    public ResponseEntity<Todo> updateTodo(@PathVariable Long id, @Validated @RequestBody Todo todo) {
        return todoService.findById(id)
                .map(existingTodo -> {
                    existingTodo.setTitle(todo.getTitle());
                    existingTodo.setDescription(todo.getDescription());
                    Todo updatedTodo = todoService.saveOrUpdate(existingTodo);
                    return ResponseEntity.ok(updatedTodo);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Deletes a Todo item by its ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
        try {
            todoService.delete(id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}

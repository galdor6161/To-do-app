package com.todoapp.todo.service;

import com.todoapp.todo.entity.Todo;
import com.todoapp.todo.repository.TodoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TodoService {
    private final TodoRepository todoRepository;

    // Constructor for dependency injection
    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    // Retrieves a paginated list of Todo items
    public Page<Todo> getTodos(int page, int size, String sortBy, String direction) {
        if (page < 0 || size <= 0) {
            throw new IllegalArgumentException("Page index must not be less than zero and size must be greater than zero.");
        }
        // Sorts the results based on the given direction and property
        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        return todoRepository.findAll(PageRequest.of(page, size, sort));
    }

    // Saves or updates a Todo item, ensuring no duplicate titles
    public Todo saveOrUpdate(Todo todo) {
        if (todoRepository.existsByTitleIgnoreCase(todo.getTitle())) {
            throw new IllegalArgumentException("A todo with the same title already exists.");
        }
        return todoRepository.save(todo);
    }

    // Deletes a Todo item by its ID
    public void delete(Long id) {
        if (todoRepository.existsById(id)) {
            todoRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Todo with ID " + id + " does not exist.");
        }
    }

    // Finds a Todo item by its ID
    public Optional<Todo> findById(Long id) {
        return todoRepository.findById(id);
    }
}

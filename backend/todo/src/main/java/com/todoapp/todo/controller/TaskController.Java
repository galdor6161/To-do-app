package com.todoapp.todo.controller;

import com.todoapp.todo.dto.TaskDto;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @PostMapping("/add")
    public String addTask(@RequestBody TaskDto taskDto) {
        // Task ekleme işlemini burada yapın
        return ResponseEntity.ok("Gorev basariyla eklendi");
    }
}

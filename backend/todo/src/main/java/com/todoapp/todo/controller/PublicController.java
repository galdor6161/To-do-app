package com.todoapp.todo.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @GetMapping("/")
    public String publicHome() {
        return "This is a public endpoint!";
    }

    @PostMapping("/")
    public String receiveTask(@RequestBody String message) {
        System.out.println("Yeni bir görev bildirimi alindi: " + message);
        return "Görev başariyla alindi!";
    }
}

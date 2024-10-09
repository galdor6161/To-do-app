package com.todoapp.todo.dto;

public class TaskDto {
    private String taskName;

    // Parametresiz yapıcı (No-arg constructor)
    public TaskDto() {
    }

    // Getter ve Setter
    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
}

package com.todoapp.todo.repository;

import com.todoapp.todo.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
    boolean existsByTitleIgnoreCase(String title);
}
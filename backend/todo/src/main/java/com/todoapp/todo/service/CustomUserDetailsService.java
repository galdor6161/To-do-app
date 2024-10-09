package com.todoapp.todo.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // For simplicity, using a hardcoded user. Replace this with user fetching from a database.
        if ("testuser".equals(username)) {
            return new User("testuser", "$2a$10$eB2qX/ZCJfW9WzI10v2ZaOinAe2kF1gd6OZ3vwe5u.hN6EotSOx0S", new ArrayList<>()); // Password: "password" (hashed)
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }
}

package com.todoapp.todo.service;

import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@Primary
public class UserDetailsServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Örnek kullanıcı doğrulama, bu kısmı veritabanı ile entegre edebilirsiniz.
        if ("user".equals(username)) {
            return new User("user", "$2a$10$D9e.KFkHd8BJphFjsMBVSe5yFSKjKkT35znhK/GzOlq5M7szI0Hm6", new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("Kullanici bulunamadi: " + username);
        }
    }
}

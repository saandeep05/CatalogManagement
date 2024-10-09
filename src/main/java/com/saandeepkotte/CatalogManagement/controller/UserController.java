package com.saandeepkotte.CatalogManagement.controller;

import com.saandeepkotte.CatalogManagement.model.User;
import com.saandeepkotte.CatalogManagement.repository.UserRepository;
import com.saandeepkotte.CatalogManagement.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Data
    @AllArgsConstructor
    private class UserResponse {
        private String username;
        private String token;
    }

    @PostMapping("/register")
    public UserResponse addUser(@RequestBody User user) {
        User savedUser = userService.saveUser(user);
        String token = userService.generateToken(user);
        return new UserResponse(savedUser.getUsername(), token);
    }

    @PostMapping("/login")
    public UserResponse login(@RequestBody User user) {
        String token = userService.verify(user);
        return new UserResponse(user.getUsername(), token);
    }
}

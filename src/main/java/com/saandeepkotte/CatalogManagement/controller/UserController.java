package com.saandeepkotte.CatalogManagement.controller;

import com.saandeepkotte.CatalogManagement.dto.LoginRequest;
import com.saandeepkotte.CatalogManagement.dto.RegisterRequest;
import com.saandeepkotte.CatalogManagement.model.User;
import com.saandeepkotte.CatalogManagement.repository.UserRepository;
import com.saandeepkotte.CatalogManagement.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    private UserService userService;

    @Data
    @AllArgsConstructor
    private class UserResponse {
        private String username;
        private String token;
        private String role;
    }

    @PostMapping("/register")
    public UserResponse addUser(@Valid @RequestBody RegisterRequest request) {
        User user = request.toUser();
        User savedUser = userService.saveUser(user);
        String token = userService.generateToken(user);
        return new UserResponse(savedUser.getUsername(), token, savedUser.getRole());
    }

    @PostMapping("/login")
    public UserResponse login(@Valid @RequestBody LoginRequest request) {
        System.out.println(request);
        User user = request.toUser();
        String token = userService.verify(user);
        User fetchedUser = userService.findByUsername(user.getUsername());
        return new UserResponse(user.getUsername(), token, fetchedUser.getRole());
    }
}

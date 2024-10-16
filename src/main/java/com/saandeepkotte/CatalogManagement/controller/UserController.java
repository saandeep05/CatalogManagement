package com.saandeepkotte.CatalogManagement.controller;

import com.saandeepkotte.CatalogManagement.dto.LoginRequest;
import com.saandeepkotte.CatalogManagement.dto.RegisterRequest;
import com.saandeepkotte.CatalogManagement.exceptions.InvalidCredentialsException;
import com.saandeepkotte.CatalogManagement.model.User;
import com.saandeepkotte.CatalogManagement.repository.UserRepository;
import com.saandeepkotte.CatalogManagement.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    private UserService userService;
    private static final Logger logger = LogManager.getLogger();

    @Data
    @AllArgsConstructor
    private class UserResponse {
        private String username;
        private String token;
        private String role;
    }

    @PostMapping("/register")
    public UserResponse addUser(@Valid @RequestBody RegisterRequest request) {
        logger.debug("registering user " + request.toString());
        User user = request.toUser();
        User savedUser = userService.saveUser(user);
        String token = userService.generateToken(user);
        logger.debug("registered token " + token);
        return new UserResponse(savedUser.getUsername(), token, savedUser.getRole());
    }

    @PostMapping("/login")
    public UserResponse login(@Valid @RequestBody LoginRequest request) throws UsernameNotFoundException, InvalidCredentialsException {
        logger.debug("logging user" + request);
        User user = request.toUser();
        User fetchedUser = userService.findByUsername(user.getUsername());
        try {
            String token = userService.verify(user);
            logger.debug("logged in token " + token);
            return new UserResponse(user.getUsername(), token, fetchedUser.getRole());
        } catch (Exception e) {
            logger.error("Incorrect password");
            throw new InvalidCredentialsException("Incorrect password for username '" + user.getUsername() + "'");
        }
    }
}

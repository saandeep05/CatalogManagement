package com.saandeepkotte.CatalogManagement.dto;

import com.saandeepkotte.CatalogManagement.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest extends LoginRequest {
    @NotBlank(message = "Email cannot be empty")
    @Email(message = "invalid email address")
    String email;

    public User toUser() {
        User user = new User();
        user.setUsername(getUsername());
        user.setEmail(getEmail());
        user.setPassword(getPassword());
        return user;
    }
}

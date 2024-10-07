package com.saandeepkotte.CatalogManagement.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sessions")
public class HomeController {
    @GetMapping("/csrf-token")
    public CsrfToken getCsrfToken(HttpServletRequest http) {
        return (CsrfToken) http.getAttribute("_csrf");
    }

    @GetMapping("/session-id")
    public String getSessionId(HttpServletRequest http) {
        return http.getSession().getId();
    }
}

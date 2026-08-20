package com.example.employee_management.controller;

import com.example.employee_management.entity.User;
import com.example.employee_management.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public User register(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String role) {

        return authService.register(username, password, role);
    }
    @PostMapping("/login")
    public String login(
            @RequestParam String username,
            @RequestParam String password) {

        return authService.login(username, password);
    }
}
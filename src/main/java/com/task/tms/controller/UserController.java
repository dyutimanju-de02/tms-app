package com.task.tms.controller;

import com.task.tms.dto.*;
import com.task.tms.model.User;
import com.task.tms.repository.UserRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User API")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // REGISTER
    @PostMapping("/register")
    @Operation(summary = "Register a new user")
    public UserResponse register(@RequestBody UserRegisterRequest request) {

        userRepository.findByEmail(request.getEmail())
                .ifPresent(u -> {
                    throw new RuntimeException("Email already registered");
                });

        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword()); // hash later
        user.setRoles(List.of("USER"));

        userRepository.save(user);

        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());

        return response;
    }

    // LOGIN (placeholder)
    @PostMapping("/login")
    @Operation(summary = "Login user")
    public LoginResponse login(@RequestBody LoginRequest request) {
        LoginResponse response = new LoginResponse();
        response.setAccessToken("jwt-access-token");
        response.setRefreshToken("jwt-refresh-token");
        return response;
    }

    // GET PROFILE
    @GetMapping("/{id}")
    @Operation(summary = "Get user profile")
    public UserResponse getProfile(@PathVariable String id) {
        User user = userRepository.findById(id).orElseThrow();

        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());

        return response;
    }
}
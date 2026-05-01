package com.example.scholarship.controller;

import com.example.scholarship.dto.LoginRequest;
import com.example.scholarship.dto.RegisterRequest;
import com.example.scholarship.dto.RegisterStudentRequest;
import com.example.scholarship.dto.AuthResponse;
import com.example.scholarship.model.Users;
import com.example.scholarship.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        Users user = authService.login(request);
        return ResponseEntity.ok(new AuthResponse("Login successful", user));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        Users user = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new AuthResponse("Account created successfully", user));
    }

    @PostMapping("/register/student")
    public ResponseEntity<AuthResponse> registerStudent(
            @Valid @RequestBody RegisterStudentRequest request) {
        Users user = authService.registerStudent(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new AuthResponse("Student account created successfully", user));
    }
}
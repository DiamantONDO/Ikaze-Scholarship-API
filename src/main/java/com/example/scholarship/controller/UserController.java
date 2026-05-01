package com.example.scholarship.controller;

import com.example.scholarship.model.Users;
import com.example.scholarship.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/students")
    public ResponseEntity<List<Users>> getStudents() {
        return ResponseEntity.ok(userService.getStudents());
    }

    @GetMapping("/sponsors")
    public ResponseEntity<List<Users>> getSponsors() {
        return ResponseEntity.ok(userService.getSponsors());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Users> getById(@PathVariable String id) {
        return ResponseEntity.ok(userService.getById(id));
    }
}
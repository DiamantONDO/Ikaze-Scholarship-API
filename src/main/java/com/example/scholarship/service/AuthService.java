package com.example.scholarship.service;

import com.example.scholarship.dto.LoginRequest;
import com.example.scholarship.dto.RegisterRequest;
import com.example.scholarship.dto.RegisterStudentRequest;
import com.example.scholarship.exception.DuplicateResourceException;
import com.example.scholarship.exception.UnauthorizedException;
import com.example.scholarship.model.Users;
import com.example.scholarship.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    public Users login(LoginRequest request) {
        return userRepository
                .findByEmailAndPassword(request.getEmail(), request.getPassword())
                .orElseThrow(() -> new UnauthorizedException("Invalid email or password"));
    }

    public Users register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("An account with this email already exists");
        }

        Users user = Users.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(request.getPassword())
                .role(request.getRole())
                .build();

        return userRepository.save(user);
    }

    public Users registerStudent(RegisterStudentRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("An account with this email already exists");
        }

        Users user = Users.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(request.getPassword())
                .role("student")
                .age(request.getAge())
                .sex(request.getSex())
                .phone(request.getPhone())
                .year(request.getYear())
                .field(request.getField())
                .highSchool(request.getHighSchool())
                .fatherName(request.getFatherName())
                .fatherPhone(request.getFatherPhone())
                .motherName(request.getMotherName())
                .motherPhone(request.getMotherPhone())
                .build();

        return userRepository.save(user);
    }
}
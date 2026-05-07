package com.example.scholarship.service;

import com.example.scholarship.dto.LoginRequest;
import com.example.scholarship.dto.RegisterRequest;
import com.example.scholarship.dto.RegisterStudentRequest;
import com.example.scholarship.exception.DuplicateResourceException;
import com.example.scholarship.exception.UnauthorizedException;
import com.example.scholarship.model.Users;
import com.example.scholarship.repository.UserRepository;
import com.example.scholarship.utils.SanitizationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final SanitizationUtils sanitizer;

    public Users login(LoginRequest request) {
        String email = sanitizer.sanitizeEmail(request.getEmail());
        String password = request.getPassword().trim();

        return userRepository
                .findByEmailAndPassword(email, password)
                .orElseThrow(() -> new UnauthorizedException("Invalid email or password"));
    }

    public Users register(RegisterRequest request) {
        String email = sanitizer.sanitizeEmail(request.getEmail());

        if (userRepository.existsByEmail(email)) {
            throw new DuplicateResourceException("An account with this email already exists");
        }

        Users user = Users.builder()
                .fullName(sanitizer.sanitize(request.getFullName()))
                .email(email)
                .password(request.getPassword().trim())
                .role(sanitizer.sanitize(request.getRole()))
                .build();

        return userRepository.save(user);
    }

    public Users registerStudent(RegisterStudentRequest request) {
        String email = sanitizer.sanitizeEmail(request.getEmail());

        if (userRepository.existsByEmail(email)) {
            throw new DuplicateResourceException("An account with this email already exists");
        }

        Users user = Users.builder()
                .fullName(sanitizer.sanitize(request.getFullName()))
                .email(email)
                .password(request.getPassword().trim())
                .role("student")
                .age(sanitizer.sanitize(request.getAge()))
                .sex(sanitizer.sanitize(request.getSex()))
                .phone(sanitizer.sanitize(request.getPhone()))
                .year(sanitizer.sanitize(request.getYear()))
                .field(sanitizer.sanitize(request.getField()))
                .highSchool(sanitizer.sanitize(request.getHighSchool()))
                .fatherName(sanitizer.sanitize(request.getFatherName()))
                .fatherPhone(sanitizer.sanitize(request.getFatherPhone()))
                .motherName(sanitizer.sanitize(request.getMotherName()))
                .motherPhone(sanitizer.sanitize(request.getMotherPhone()))
                .build();

        return userRepository.save(user);
    }
}
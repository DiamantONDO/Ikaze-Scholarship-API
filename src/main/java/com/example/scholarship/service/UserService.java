package com.example.scholarship.service;

import com.example.scholarship.exception.ResourceNotFoundException;
import com.example.scholarship.model.Users;
import com.example.scholarship.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository usersRepository;

    public List<Users> getStudents() {
        return usersRepository.findByRole("student");
    }

    public List<Users> getSponsors() {
        return usersRepository.findByRole("sponsor");
    }

    public Users getById(String id) {
        return usersRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    public Users getByEmail(String email) {
        return usersRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
    }
}
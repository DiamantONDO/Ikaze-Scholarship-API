package com.example.scholarship.service;

import com.example.scholarship.dto.ScholarshipRequest;
import com.example.scholarship.exception.ResourceNotFoundException;
import com.example.scholarship.model.Scholarship;
import com.example.scholarship.repository.ScholarshipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScholarshipService {

    private final ScholarshipRepository scholarshipRepository;

    public List<Scholarship> getAll() {
        return scholarshipRepository.findAll();
    }

    public List<Scholarship> getActive() {
        return scholarshipRepository.findByStatus("active");
    }

    public List<Scholarship> getBySponsor(String sponsorId) {
        return scholarshipRepository.findBySponsorId(sponsorId);
    }

    public Scholarship getById(String id) {
        return scholarshipRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Scholarship not found with id: " + id));
    }

    public Scholarship create(String sponsorId, ScholarshipRequest request) {
        Scholarship scholarship = Scholarship.builder()
                .sponsorId(sponsorId)
                .title(request.getTitle())
                .field(request.getField())
                .amount(Long.valueOf(request.getAmount()))//Cause of Long
                .deadline(request.getDeadline())
                .description(request.getDescription())
                .requirements(request.getRequirements())
                .status(request.getStatus() != null ? request.getStatus() : "active")
                .createdAt(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME))
                .build();

        return scholarshipRepository.save(scholarship);
    }

    public void delete(String id) {
        if (!scholarshipRepository.existsById(id)) {
            throw new ResourceNotFoundException("Scholarship not found with id: " + id);
        }
        scholarshipRepository.deleteById(id);
    }
}
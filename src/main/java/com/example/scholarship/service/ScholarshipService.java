package com.example.scholarship.service;

import com.example.scholarship.dto.ScholarshipRequest;
import com.example.scholarship.exception.ResourceNotFoundException;
import com.example.scholarship.model.Scholarship;
import com.example.scholarship.repository.ScholarshipRepository;
import com.example.scholarship.utils.SanitizationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScholarshipService {

    private final ScholarshipRepository scholarshipRepository;
    private final SanitizationUtils sanitizer;

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
        List<String> sanitizedRequirements = request.getRequirements() == null ? List.of() :
                request.getRequirements().stream()
                        .map(sanitizer::sanitize)
                        .collect(Collectors.toList());

        Scholarship scholarship = Scholarship.builder()
                .sponsorId(sponsorId)
                .title(sanitizer.sanitize(request.getTitle()))
                .field(sanitizer.sanitize(request.getField()))
                .amount(request.getAmount())
                .deadline(sanitizer.sanitize(request.getDeadline()))
                .description(sanitizer.sanitize(request.getDescription()))
                .requirements(sanitizedRequirements)
                .status(request.getStatus() != null ? sanitizer.sanitize(request.getStatus()) : "active")
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
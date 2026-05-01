package com.example.scholarship.controller;

import com.example.scholarship.dto.ApiResponse;
import com.example.scholarship.dto.ApplicationRequest;
import com.example.scholarship.dto.ApplicationStatusRequest;
import com.example.scholarship.model.Application;
import com.example.scholarship.model.Scholarship;
import com.example.scholarship.service.ApplicationService;
import com.example.scholarship.service.ScholarshipService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;
    private final ScholarshipService scholarshipService;

    @GetMapping
    public ResponseEntity<List<Application>> getAll() {
        return ResponseEntity.ok(applicationService.getAll());
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Application>> getByStudent(@PathVariable String studentId) {
        return ResponseEntity.ok(applicationService.getByStudent(studentId));
    }

    @GetMapping("/sponsor/{sponsorId}")
    public ResponseEntity<List<Application>> getBySponsor(@PathVariable String sponsorId) {
        List<String> scholarshipIds = scholarshipService.getBySponsor(sponsorId)
                .stream()
                //before: s.getId()  after: Scholarship::getId
                .map(Scholarship::getId)
                .collect(Collectors.toList());
        return ResponseEntity.ok(applicationService.getBySponsorScholarships(scholarshipIds));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Application> getById(@PathVariable String id) {
        return ResponseEntity.ok(applicationService.getById(id));
    }

    @PostMapping("/student/{studentId}")
    public ResponseEntity<Application> apply(
            @PathVariable String studentId,
            @Valid @RequestBody ApplicationRequest request) {
        Application application = applicationService.apply(studentId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(application);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Application> updateStatus(
            @PathVariable String id,
            @Valid @RequestBody ApplicationStatusRequest request) {
        Application application = applicationService.updateStatus(id, request);
        return ResponseEntity.ok(application);
    }
}
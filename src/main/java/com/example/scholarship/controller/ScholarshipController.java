package com.example.scholarship.controller;

import com.example.scholarship.dto.ApiResponse;
import com.example.scholarship.dto.ScholarshipRequest;
import com.example.scholarship.model.Scholarship;
import com.example.scholarship.service.ScholarshipService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/scholarships")
@RequiredArgsConstructor
public class ScholarshipController {

    private final ScholarshipService scholarshipService;

    @GetMapping
    public ResponseEntity<List<Scholarship>> getAll() {
        return ResponseEntity.ok(scholarshipService.getAll());
    }

    @GetMapping("/active")
    public ResponseEntity<List<Scholarship>> getActive() {
        return ResponseEntity.ok(scholarshipService.getActive());
    }

    @GetMapping("/sponsor/{sponsorId}")
    public ResponseEntity<List<Scholarship>> getBySponsor(@PathVariable String sponsorId) {
        return ResponseEntity.ok(scholarshipService.getBySponsor(sponsorId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Scholarship> getById(@PathVariable String id) {
        return ResponseEntity.ok(scholarshipService.getById(id));
    }

    @PostMapping("/sponsor/{sponsorId}")
    public ResponseEntity<Scholarship> create(
            @PathVariable String sponsorId,
            @Valid @RequestBody ScholarshipRequest request) {
        Scholarship scholarship = scholarshipService.create(sponsorId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(scholarship);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable String id) {
        scholarshipService.delete(id);
        return ResponseEntity.ok(new ApiResponse("Scholarship deleted successfully", null));
    }
}
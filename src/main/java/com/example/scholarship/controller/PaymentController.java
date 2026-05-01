package com.example.scholarship.controller;

import com.example.scholarship.dto.PaymentRequest;
import com.example.scholarship.model.Payment;
import com.example.scholarship.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping
    public ResponseEntity<List<Payment>> getAll() {
        return ResponseEntity.ok(paymentService.getAll());
    }

    @GetMapping("/sponsor/{sponsorId}")
    public ResponseEntity<List<Payment>> getBySponsor(@PathVariable String sponsorId) {
        return ResponseEntity.ok(paymentService.getBySponsor(sponsorId));
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Payment>> getByStudent(@PathVariable String studentId) {
        return ResponseEntity.ok(paymentService.getByStudent(studentId));
    }

    @PostMapping("/sponsor/{sponsorId}")
    public ResponseEntity<Payment> process(
            @PathVariable String sponsorId,
            @Valid @RequestBody PaymentRequest request) {
        Payment payment = paymentService.process(sponsorId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(payment);
    }
}
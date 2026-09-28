package com.example.scholarship.service;

import com.example.scholarship.dto.PaymentRequest;
import com.example.scholarship.exception.BadRequestException;
import com.example.scholarship.exception.ResourceNotFoundException;
import com.example.scholarship.model.Payment;
import com.example.scholarship.repository.PaymentRepository;
import com.example.scholarship.repository.ScholarshipRepository;
import com.example.scholarship.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;
    private final ScholarshipRepository scholarshipRepository;

    public List<Payment> getAll() {
        return paymentRepository.findAll();
    }

    public List<Payment> getBySponsor(String sponsorId) {
        return paymentRepository.findBySponsorId(sponsorId);
    }

    public List<Payment> getByStudent(String studentId) {
        return paymentRepository.findByStudentId(studentId);
    }

    public Payment process(String sponsorId, PaymentRequest request) {
        userRepository.findById(request.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));

        scholarshipRepository.findById(request.getScholarshipId())
                .orElseThrow(() -> new ResourceNotFoundException("Scholarship not found"));

        paymentRepository.findByStudentIdAndSponsorIdAndMonth(
                request.getStudentId(),
                request.getScholarshipId(),
                request.getMonth()
        ).ifPresent(p -> { throw new BadRequestException("This student has already been paid for this month"); });

        Payment payment = Payment.builder()
                .sponsorId(sponsorId)
                .studentId(request.getStudentId())
                .scholarshipId(request.getScholarshipId())
                .amount(request.getAmount())
                .month(request.getMonth())
                .status("paid")
                .date(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME))
                .build();

        return paymentRepository.save(payment);
    }
}
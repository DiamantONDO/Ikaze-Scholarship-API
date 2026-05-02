package com.example.scholarship.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PaymentRequest {
    @NotBlank(message = "Student Id is required")
    private String studentId;

    @NotBlank(message = "Scholarship Id is required")
    private String scholarshipId;

    @NotBlank(message = "Month is required")
    private String month;

    @NotNull(message = "Amount is required")
    private Long amount;
}

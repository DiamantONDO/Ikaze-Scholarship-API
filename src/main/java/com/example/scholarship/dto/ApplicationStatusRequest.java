package com.example.scholarship.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ApplicationStatusRequest {
    @NotBlank(message = "Status is required")
    //"Approve" or "Rejected"
    private String status;
}
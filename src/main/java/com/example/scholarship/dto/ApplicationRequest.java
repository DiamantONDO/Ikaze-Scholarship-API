package com.example.scholarship.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ApplicationRequest {
    @NotNull(message = "Scholarship Id is required")
    private String scholarshipId;

    private String fullName;
    private String age;
    private String sex;
    private String idCardName;
    private String idCardData;
    private String equivalenceName;
    private String equivalenceData;
    private String transcriptName;
    private String  transcriptData;
}

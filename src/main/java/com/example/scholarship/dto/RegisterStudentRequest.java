package com.example.scholarship.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterStudentRequest {
    @NotBlank(message = "Full Name is required")
    private String fullName;

    @NotBlank(message = "Your Email is required")
    private String email;

    @NotBlank(message = "Your PAssword is required")
    private String password;

    private String age;
    private String sex;
    private String phone;
    private String year;
    private String field;
    private String highSchool;
    private String fatherName;
    private String fatherPhone;
    private String motherName;
    private String motherPhone;
}

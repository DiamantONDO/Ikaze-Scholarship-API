package com.example.scholarship.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.util.List;

@Data
public class ScholarshipRequest {
    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Field is required")
    private String field;

    @NotNull(message = "Amount is required")
    private Long amount;

    @NotBlank(message = "Deadline is required")
    private String deadline;

    @NotBlank(message = "Description is required")
    private String description;

    private List<String> requirements;
    private String status;

}

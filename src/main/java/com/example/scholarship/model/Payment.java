package com.example.scholarship.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "payments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {
    @Id
    private String id;
    private String sponsorId;
    private String studentId;
    private String scholarshipId;
    private Long amount;
    private String month;
    private String status;
    private String date;
}

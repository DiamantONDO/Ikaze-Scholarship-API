package com.example.scholarship.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "applications")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Application {
    @Id
    private String id;
    private String studentId;
    private String status;

    private String fullName;
    private String age;
    private String se;
    private String idCardName;
    private String idCardDate;
    private String equivalenceName;
    private String equivalenceData;
    private String transcriptName;
    private String transcriptData;
}

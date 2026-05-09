package com.example.scholarship.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Document(collection = "scholarships")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Scholarship {
    @Id
    private String id;
    private String sponsorId;
    private String title;
    private String field;
    private Long amount;
    private String deadline;
    private String description;
    private List<String> requirements;
    private String status;
    private String createdAt;
}

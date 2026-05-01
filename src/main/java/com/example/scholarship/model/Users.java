package com.example.scholarship.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;

@Document(collection = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor

@Builder
public class Users {
    @Id
    private String id;
    private String fullName;

    @Indexed(unique = true)
    private String email;
    private String password;
    private String role;

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

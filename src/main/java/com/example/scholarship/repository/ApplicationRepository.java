package com.example.scholarship.repository;

import com.example.scholarship.model.Application;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationRepository extends MongoRepository<Application, String> {
    List<Application> findByStudentId(String studentId);
    List<Application> findByScholarshipId(String scholarshipId);
    List<Application> findByScholarshipIdIn(List<String> scholarshipIds);
    Optional<Application> findByStudentIdAndScholarshipId(String studentId, String scholarshipId);
    List<Application> findByStatus(String status);
}
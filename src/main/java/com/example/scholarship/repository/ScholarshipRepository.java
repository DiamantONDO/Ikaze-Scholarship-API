package com.example.scholarship.repository;

import com.example.scholarship.model.Scholarship;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ScholarshipRepository extends MongoRepository<Scholarship, String> {
    List<Scholarship> findBySponsorId(String sponsorId);
    List<Scholarship> findByStatus(String status);
    List<Scholarship> findBySponsorIdAndStatus(String sponsorId, String status);
}
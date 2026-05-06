package com.example.scholarship.repository;

import com.example.scholarship.model.Payment;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;


@Repository
public interface PaymentRepository extends MongoRepository<Payment, String> {
    List<Payment> findBySponsorId(String sponsorId);
    List<Payment> findByStudentId(String studentId);
    List<Payment> findByScholarshipId(String scholarshipId);
    Optional<Payment> findByStudentIdAndSponsorIdAndMonth(
            String studentId, String sponsorId, String month
    );
}

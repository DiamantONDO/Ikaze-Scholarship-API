package com.example.scholarship.repository;

import com.example.scholarship.model.Users;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<Users, String> {
    Optional<Users> findByEmail(String email);
    Optional<Users> findByEmailAndPassword(String email, String password);
    List<Users> findByRole(String role);
    boolean existsByEmail(String email);
}

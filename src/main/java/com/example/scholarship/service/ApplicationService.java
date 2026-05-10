package com.example.scholarship.service;

import com.example.scholarship.dto.ApplicationRequest;
import com.example.scholarship.dto.ApplicationStatusRequest;
import com.example.scholarship.exception.BadRequestException;
import com.example.scholarship.exception.ResourceNotFoundException;
import com.example.scholarship.model.Application;
import com.example.scholarship.repository.ApplicationRepository;
import com.example.scholarship.repository.ScholarshipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final ScholarshipRepository scholarshipRepository;

    public List<Application> getAll() {
        return applicationRepository.findAll();
    }

    public List<Application> getByStudent(String studentId) {
        return applicationRepository.findByStudentId(studentId);
    }

    public List<Application> getBySponsorScholarships(List<String> scholarshipIds) {
        return applicationRepository.findByScholarshipIdIn(scholarshipIds);
    }

    public Application getById(String id) {
        return applicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found with id: " + id));
    }

    public Application apply(String studentId, ApplicationRequest request) {
        // Check scholarship exists
        scholarshipRepository.findById(request.getScholarshipId())
                .orElseThrow(() -> new ResourceNotFoundException("Scholarship not found"));

        // Check already applied
        applicationRepository.findByStudentIdAndScholarshipId(studentId, request.getScholarshipId())
                .ifPresent(a -> { throw new BadRequestException("You have already applied for this scholarship"); });

        Application application = Application.builder()
                .studentId(studentId)
                .scholarshipId(request.getScholarshipId())
                .status("Pending")
                .processed(false)
                .date(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME))
                .fullName(request.getFullName())
                .age(request.getAge())
                .sex(request.getSex())
                .idCardName(request.getIdCardName())
                .idCardData(request.getIdCardData())
                .equivalenceName(request.getEquivalenceName())
                .equivalenceData(request.getEquivalenceData())
                .transcriptName(request.getTranscriptName())
                .transcriptData(request.getTranscriptData())
                .build();

        return applicationRepository.save(application);
    }

    public Application updateStatus(String id, ApplicationStatusRequest request) {
        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found with id: " + id));

        String status = request.getStatus();
        if (!status.equals("Approved") && !status.equals("Rejected")) {
            throw new BadRequestException("Status must be Approved or Rejected");
        }

        application.setStatus(status);
        application.setProcessed(true);
        return applicationRepository.save(application);
    }
}

package org.sumerge.careerpackageservice.Service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.sumerge.careerpackageservice.Dto.Request.AssignCareerPackageRequest;
import org.sumerge.careerpackageservice.Dto.Request.SendNotificationRequest;
import org.sumerge.careerpackageservice.Dto.UserCareerPackageDTO;
import org.sumerge.careerpackageservice.Entity.CareerPackageTemplate;
import org.sumerge.careerpackageservice.Entity.UserCareerPackage;
import org.sumerge.careerpackageservice.Enums.PackageStatus;
import org.sumerge.careerpackageservice.Mapper.UserCareerPackageMapper;
import org.sumerge.careerpackageservice.Repository.CareerPackageTemplateRepository;
import org.sumerge.careerpackageservice.Repository.UserCareerPackageRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserCareerPackageService {


    private final UserCareerPackageRepository userCareerPackageRepository;
    private final CareerPackageTemplateRepository templateRepository;
    private final UserCareerPackageMapper mapper;
    private final KafkaTemplate<String, SendNotificationRequest> kafkaTemplate;

    public UserCareerPackageService(UserCareerPackageRepository userCareerPackageRepository, CareerPackageTemplateRepository templateRepository, UserCareerPackageMapper mapper, KafkaTemplate<String, SendNotificationRequest> kafkaTemplate) {
        this.userCareerPackageRepository = userCareerPackageRepository;
        this.templateRepository = templateRepository;
        this.mapper = mapper;
        this.kafkaTemplate = kafkaTemplate;
    }

    public List<UserCareerPackage> getAll() {
        return userCareerPackageRepository.findAll();
    }

    public Optional<UserCareerPackage> getById(UUID id) {
        return userCareerPackageRepository.findById(id);
    }

    public UserCareerPackage create(UserCareerPackage obj) {
        return userCareerPackageRepository.save(obj);
    }

    public void delete(UUID id) {
        userCareerPackageRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public UserCareerPackage getFullyLoadedPackageByUserId(UUID userId) {
        UserCareerPackage userCareerPackage = userCareerPackageRepository.findByUserId(userId);

        if (userCareerPackage == null) return null;

        // Force fetch template and all sections + fields
        userCareerPackage.getTemplate().getSections().forEach(section -> {
            section.getFields().size(); // triggers fetch
        });

        // Force fetch user responses and nested field responses
        userCareerPackage.getSectionResponses().forEach(response -> {
            response.getFieldResponses().size(); // triggers fetch
        });

        return userCareerPackage;
    }

    public UserCareerPackage updateUserCareerPackage(UUID id, UserCareerPackage request) {
        UserCareerPackage userCareerPackage = userCareerPackageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Career package not found"));

        userCareerPackage.setStatus(PackageStatus.valueOf(String.valueOf(request.getStatus())));
        userCareerPackage.setReviewerComment(String.valueOf(request.getReviewerComment()));

        if (request.getStatus() == PackageStatus.UNDER_REVIEW) {
            sendNotificationToManager(request);
        }
        else{
            sendNotificationToEmployee(request);
        }

        return userCareerPackageRepository.save(userCareerPackage);
    }

    public void sendNotificationToManager(UserCareerPackage userCareerPackage) {
        SendNotificationRequest sendNotificationRequest = new SendNotificationRequest(
                userCareerPackage.getUserId(),
                userCareerPackage.getReviewerId(),
                "An Employee has submitted his career package for review",
                "SUBMISSION"
        );
        try {
            kafkaTemplate.send("career-package-submitted", sendNotificationRequest);
        } catch (Exception e) {

            throw new RuntimeException("Failed to send message to Kafka: " + e.getMessage(), e);
        }
    }

    public void sendNotificationToEmployee(UserCareerPackage userCareerPackage) {

        String message = "";
        String type = "";

        if (userCareerPackage.getStatus() == PackageStatus.APPROVED) {
            message = "Your manager has approved your career package submission";
            type = "APPROVAL";
        }
        else if (userCareerPackage.getStatus() == PackageStatus.REJECTED) {
            message = "Your manager has rejected your career package submission";
            type = "REJECTION";
        }

        SendNotificationRequest sendNotificationRequest = new SendNotificationRequest(
                userCareerPackage.getReviewerId(),
                userCareerPackage.getUserId(),
                message,
                type
        );
        try {
            kafkaTemplate.send("career-package-submitted", sendNotificationRequest);
        } catch (Exception e) {

            throw new RuntimeException("Failed to send message to Kafka: " + e.getMessage(), e);
        }
    }

    public UserCareerPackageDTO assignPackage(AssignCareerPackageRequest request) {
        CareerPackageTemplate template = templateRepository.findById(request.getTemplateId())
                .orElseThrow(() -> new EntityNotFoundException("Template not found"));

        UserCareerPackage userCareerPackage = new UserCareerPackage(
                request.getUserId(),
                request.getReviewerId(),
                request.getStatus(),
                template
        );


        userCareerPackageRepository.save(userCareerPackage);
        return mapper.toDto(userCareerPackage);
    }


}
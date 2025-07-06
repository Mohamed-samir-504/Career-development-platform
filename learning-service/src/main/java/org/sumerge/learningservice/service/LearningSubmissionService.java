package org.sumerge.learningservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.sumerge.learningservice.dto.SendNotificationRequest;
import org.sumerge.learningservice.dto.submission.LearningSubmissionDTO;
import org.sumerge.learningservice.entity.LearningMaterialTemplate;
import org.sumerge.learningservice.entity.LearningSectionResponse;
import org.sumerge.learningservice.entity.LearningSubmission;
import org.sumerge.learningservice.enums.SubmissionStatus;
import org.sumerge.learningservice.mapper.LearningMaterialMapper;
import org.sumerge.learningservice.repository.LearningMaterialTemplateRepository;
import org.sumerge.learningservice.repository.LearningSubmissionRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LearningSubmissionService {

    private final LearningSubmissionRepository submissionRepository;
    private final LearningMaterialTemplateRepository templateRepository;
    private final LearningMaterialMapper mapper;
    private final KafkaTemplate<String, SendNotificationRequest> kafkaTemplate;

    public List<LearningSubmissionDTO> getSubmissionsByUser(UUID userId) {
        return submissionRepository.findByUserId(userId)
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public LearningSubmissionDTO getByUserAndTemplate(UUID userId, UUID templateId) {
        LearningSubmission submission = submissionRepository.findByUserIdAndTemplateId(userId, templateId)
                .orElseThrow(() -> new RuntimeException("Submission not found"));

        return mapper.toDto(submission);
    }


    public LearningSubmissionDTO saveSubmission(LearningSubmissionDTO submissionDto) {
        LearningSubmission entity = mapper.toEntity(submissionDto);

        UUID templateId = submissionDto.getTemplateId();
        LearningMaterialTemplate template = templateRepository.findById(templateId)
                .orElseThrow(() -> new RuntimeException("Template not found"));

        entity.setTemplate(template);

        if (entity.getSectionResponses() != null) {
            for (LearningSectionResponse section : entity.getSectionResponses()) {
                section.setLearningSubmission(entity); // Important backref

            }
        }

        return mapper.toDto(submissionRepository.save(entity));
    }

    public List<LearningSubmissionDTO> getSubmissionsByTemplate(UUID templateId) {
        return submissionRepository.findByTemplateId(templateId)
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    public List<LearningSubmissionDTO> getSubmissionsByManagerId(UUID managerId) {
        return submissionRepository.findByManagerId(managerId)
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    public LearningSubmissionDTO getSubmission(UUID submissionId) {
        return mapper.toDto(submissionRepository.findById(submissionId)
                .orElseThrow(() -> new RuntimeException("Submission not found")));
    }

    public void deleteSubmission(UUID id) {
        submissionRepository.deleteById(id);
    }

    public LearningSubmissionDTO reviewSubmission(UUID submissionId, boolean accepted) {

        LearningSubmission submission = submissionRepository.findById(submissionId)
              .orElseThrow(() -> new RuntimeException("Submission not found with id: " + submissionId));
        if (submission.getStatus() != SubmissionStatus.PENDING) {
            throw new IllegalStateException("Cannot review a submission that has already been reviewed.");
        }

        submission.setStatus(accepted ? SubmissionStatus.APPROVED : SubmissionStatus.REJECTED);
        submissionRepository.save(submission);

        LearningSubmissionDTO dto = mapper.toDto(submission);
        return dto;
    }

    public void sendNotificationToManager(LearningSubmissionDTO learningSubmission) {
        SendNotificationRequest sendNotificationRequest = new SendNotificationRequest(
                learningSubmission.getUserId(),
                learningSubmission.getManagerId(),
                "An Employee has submitted a learning material for review",
                "SUBMISSION"
        );
        try {
            kafkaTemplate.send("learning-material-submitted", sendNotificationRequest);
        } catch (Exception e) {

            throw new RuntimeException("Failed to send message to Kafka: " + e.getMessage(), e);
        }
    }

    public void sendNotificationToUser(LearningSubmissionDTO learningSubmission) {
        SendNotificationRequest sendNotificationRequest = new SendNotificationRequest(
                learningSubmission.getManagerId(),
                learningSubmission.getUserId(),
                "Your manager has submitted reviewed your learning material",
                "SUBMISSION"
        );
        try {
            kafkaTemplate.send("learning-submission-reviewed", sendNotificationRequest);
        } catch (Exception e) {

            throw new RuntimeException("Failed to send message to Kafka: " + e.getMessage(), e);
        }
    }
}
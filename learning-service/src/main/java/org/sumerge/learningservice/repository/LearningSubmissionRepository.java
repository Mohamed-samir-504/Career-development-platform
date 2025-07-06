package org.sumerge.learningservice.repository;

import org.sumerge.learningservice.entity.LearningSubmission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LearningSubmissionRepository extends JpaRepository<LearningSubmission, UUID> {
    List<LearningSubmission> findByUserId(UUID userId);
    List<LearningSubmission> findByTemplateId(UUID templateId);
    List<LearningSubmission> findByManagerId(UUID managerId);
    Optional<LearningSubmission> findByUserIdAndTemplateId(UUID userId, UUID templateId);
}

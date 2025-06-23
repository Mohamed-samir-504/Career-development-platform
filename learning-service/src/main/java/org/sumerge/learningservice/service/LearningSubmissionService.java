package org.sumerge.learningservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.sumerge.learningservice.dto.submission.LearningSubmissionDTO;
import org.sumerge.learningservice.entity.LearningMaterialTemplate;
import org.sumerge.learningservice.entity.LearningSectionResponse;
import org.sumerge.learningservice.entity.LearningFieldResponse;
import org.sumerge.learningservice.entity.LearningSubmission;
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

    public List<LearningSubmissionDTO> getSubmissionsByUser(UUID userId) {
        return submissionRepository.findByUserId(userId)
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public LearningSubmissionDTO saveSubmission(LearningSubmissionDTO submissionDto) {
        LearningSubmission entity = mapper.toEntity(submissionDto);

        // Load the full template object
        UUID templateId = submissionDto.getTemplateId();
        LearningMaterialTemplate template = templateRepository.findById(templateId)
                .orElseThrow(() -> new RuntimeException("Template not found"));

        entity.setTemplate(template);

        // Set back-references to parent submission
        if (entity.getSectionResponses() != null) {
            for (LearningSectionResponse section : entity.getSectionResponses()) {
                section.setLearningSubmission(entity); // Important backref
                if (section.getFieldResponses() != null) {
                    for (LearningFieldResponse field : section.getFieldResponses()) {
                        field.setSectionResponse(section); // Important backref
                    }
                }
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

    public LearningSubmissionDTO getSubmission(UUID submissionId) {
        return mapper.toDto(submissionRepository.findById(submissionId)
                .orElseThrow(() -> new RuntimeException("Submission not found")));
    }

    public void deleteSubmission(UUID id) {
        submissionRepository.deleteById(id);
    }
}
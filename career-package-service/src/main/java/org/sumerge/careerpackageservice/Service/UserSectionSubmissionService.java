
package org.sumerge.careerpackageservice.Service;

import jakarta.persistence.EntityNotFoundException;
import org.sumerge.careerpackageservice.Dto.Request.SubmitUserSectionRequest;
import org.sumerge.careerpackageservice.Dto.UserFieldSubmissionDTO;
import org.sumerge.careerpackageservice.Entity.*;
import org.sumerge.careerpackageservice.Repository.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserSectionSubmissionService {


    private final UserCareerPackageRepository careerPackageRepo;
    private final SectionTemplateRepository sectionTemplateRepo;
    private final SectionFieldTemplateRepository fieldTemplateRepo;
    private final UserSectionSubmissionRepository sectionResponseRepo;


    public UserSectionSubmissionService(UserCareerPackageRepository careerPackageRepo, SectionTemplateRepository sectionTemplateRepo, SectionFieldTemplateRepository fieldTemplateRepo, UserSectionSubmissionRepository sectionResponseRepo) {
        this.careerPackageRepo = careerPackageRepo;
        this.sectionTemplateRepo = sectionTemplateRepo;
        this.fieldTemplateRepo = fieldTemplateRepo;
        this.sectionResponseRepo = sectionResponseRepo;

    }

    public List<UserSectionSubmission> getAll() {
        return sectionResponseRepo.findAll();
    }

    public Optional<UserSectionSubmission> getById(UUID id) {
        return sectionResponseRepo.findById(id);
    }

    public UserSectionSubmission create(UserSectionSubmission obj) {
        return sectionResponseRepo.save(obj);
    }

    public void delete(UUID id) {
        sectionResponseRepo.deleteById(id);
    }

    public UserSectionSubmission submitSection(SubmitUserSectionRequest request) {
        UserCareerPackage userCareerPackage = careerPackageRepo.findById(request.getUserCareerPackageId())
                .orElseThrow(() -> new EntityNotFoundException("Career package not found"));

        SectionTemplate sectionTemplate = sectionTemplateRepo.findById(request.getSectionTemplateId())
                .orElseThrow(() -> new EntityNotFoundException("Section template not found"));

        UserSectionSubmission sectionResponse = new UserSectionSubmission();
        sectionResponse.setSectionTemplate(sectionTemplate);

        List<UserFieldSubmission> responses = request.getFieldSubmissions().stream().map(userFieldResponseDTO -> {
            SectionFieldTemplate fieldTemplate = fieldTemplateRepo.findById(userFieldResponseDTO.getFieldTemplateId())
                    .orElseThrow(() -> new EntityNotFoundException("Field template not found"));

            return new UserFieldSubmission(
                    fieldTemplate,
                    userFieldResponseDTO.getValue()
            );
        }).toList();

        sectionResponse.setFieldSubmissions(responses);
        userCareerPackage.getSectionSubmissions().add(sectionResponse);
        return sectionResponseRepo.save(sectionResponse);
    }

    public UserSectionSubmission updateSection(UUID id, SubmitUserSectionRequest request) {
        UserSectionSubmission sectionResponse = sectionResponseRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Section response not found"));

        //map existing responses by ID
        Map<UUID, UserFieldSubmission> existingResponsesById = sectionResponse.getFieldSubmissions().stream()
                .filter(r -> r.getId() != null)
                .collect(Collectors.toMap(UserFieldSubmission::getId, r -> r));


        for (UserFieldSubmissionDTO userFieldSubmissionDTO : request.getFieldSubmissions()) {
            UUID fieldResponseId = userFieldSubmissionDTO.getId();
            UserFieldSubmission existing = existingResponsesById.get(fieldResponseId);
            if (existing != null) {
                existing.setValue(userFieldSubmissionDTO.getValue());
            } else {
                throw new RuntimeException("Field response with ID " + fieldResponseId + " not found in this section");
            }
        }

        //handle new fields when updating
        if (request.getNewFieldSubmissions() != null) {
            for (UserFieldSubmissionDTO newUserField : request.getNewFieldSubmissions()) {
                SectionFieldTemplate fieldTemplate = fieldTemplateRepo.findById(newUserField.getFieldTemplateId())
                        .orElseThrow(() -> new EntityNotFoundException("Field template not found"));
                UserFieldSubmission newResponse = new UserFieldSubmission(
                        fieldTemplate,
                        newUserField.getValue()
                );

                sectionResponse.getFieldSubmissions().add(newResponse);
            }
        }

        return sectionResponseRepo.save(sectionResponse);
    }


}

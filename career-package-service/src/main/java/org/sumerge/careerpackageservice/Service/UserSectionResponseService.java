
package org.sumerge.careerpackageservice.Service;

import jakarta.persistence.EntityNotFoundException;
import org.sumerge.careerpackageservice.Dto.Request.SubmitUserSectionRequest;
import org.sumerge.careerpackageservice.Dto.UserFieldResponseDTO;
import org.sumerge.careerpackageservice.Entity.*;
import org.sumerge.careerpackageservice.Repository.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserSectionResponseService {


    private final UserCareerPackageRepository careerPackageRepo;
    private final SectionTemplateRepository sectionTemplateRepo;
    private final SectionFieldTemplateRepository fieldTemplateRepo;
    private final UserSectionResponseRepository sectionResponseRepo;


    public UserSectionResponseService(UserCareerPackageRepository careerPackageRepo, SectionTemplateRepository sectionTemplateRepo, SectionFieldTemplateRepository fieldTemplateRepo, UserSectionResponseRepository sectionResponseRepo) {
        this.careerPackageRepo = careerPackageRepo;
        this.sectionTemplateRepo = sectionTemplateRepo;
        this.fieldTemplateRepo = fieldTemplateRepo;
        this.sectionResponseRepo = sectionResponseRepo;

    }

    public List<UserSectionResponse> getAll() {
        return sectionResponseRepo.findAll();
    }

    public Optional<UserSectionResponse> getById(UUID id) {
        return sectionResponseRepo.findById(id);
    }

    public UserSectionResponse create(UserSectionResponse obj) {
        return sectionResponseRepo.save(obj);
    }

    public void delete(UUID id) {
        sectionResponseRepo.deleteById(id);
    }

    public UserSectionResponse submitSection(SubmitUserSectionRequest request) {
        UserCareerPackage userCareerPackage = careerPackageRepo.findById(request.getUserCareerPackageId())
                .orElseThrow(() -> new EntityNotFoundException("Career package not found"));

        SectionTemplate sectionTemplate = sectionTemplateRepo.findById(request.getSectionTemplateId())
                .orElseThrow(() -> new EntityNotFoundException("Section template not found"));

        UserSectionResponse sectionResponse = new UserSectionResponse();
        sectionResponse.setSectionTemplate(sectionTemplate);

        List<UserFieldResponse> responses = request.getFieldResponses().stream().map(userFieldResponseDTO -> {
            SectionFieldTemplate fieldTemplate = fieldTemplateRepo.findById(userFieldResponseDTO.getFieldTemplateId())
                    .orElseThrow(() -> new EntityNotFoundException("Field template not found"));

            return new UserFieldResponse(
                    fieldTemplate,
                    userFieldResponseDTO.getValue()
            );
        }).toList();

        sectionResponse.setFieldResponses(responses);
        userCareerPackage.getSectionResponses().add(sectionResponse);
        return sectionResponseRepo.save(sectionResponse);
    }

    public UserSectionResponse updateSection(UUID id, SubmitUserSectionRequest request) {
        UserSectionResponse sectionResponse = sectionResponseRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Section response not found"));

        //map existing responses by ID
        Map<UUID, UserFieldResponse> existingResponsesById = sectionResponse.getFieldResponses().stream()
                .filter(r -> r.getId() != null)
                .collect(Collectors.toMap(UserFieldResponse::getId, r -> r));


        for (UserFieldResponseDTO userFieldResponseDTO : request.getFieldResponses()) {
            UUID fieldResponseId = userFieldResponseDTO.getId();
            UserFieldResponse existing = existingResponsesById.get(fieldResponseId);
            if (existing != null) {
                existing.setValue(userFieldResponseDTO.getValue());
            } else {
                throw new RuntimeException("Field response with ID " + fieldResponseId + " not found in this section");
            }
        }

        //handle new fields when updating
        if (request.getNewFieldResponses() != null) {
            for (UserFieldResponseDTO newUserField : request.getNewFieldResponses()) {
                SectionFieldTemplate fieldTemplate = fieldTemplateRepo.findById(newUserField.getFieldTemplateId())
                        .orElseThrow(() -> new EntityNotFoundException("Field template not found"));
                UserFieldResponse newResponse = new UserFieldResponse(
                        fieldTemplate,
                        newUserField.getValue()
                );

                sectionResponse.getFieldResponses().add(newResponse);
            }
        }

        return sectionResponseRepo.save(sectionResponse);
    }


}

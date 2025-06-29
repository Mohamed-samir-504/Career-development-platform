
package org.sumerge.careerpackageservice.Service;

import org.sumerge.careerpackageservice.Dto.Request.SubmitUserSectionRequest;
import org.sumerge.careerpackageservice.Entity.*;
import org.sumerge.careerpackageservice.Repository.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
                .orElseThrow(() -> new RuntimeException("Career package not found"));

        SectionTemplate sectionTemplate = sectionTemplateRepo.findById(request.getSectionTemplateId())
                .orElseThrow(() -> new RuntimeException("Section template not found"));

        UserSectionResponse sectionResponse = new UserSectionResponse();
        sectionResponse.setUserCareerPackage(userCareerPackage);
        sectionResponse.setSectionTemplate(sectionTemplate);

        List<UserFieldResponse> responses = request.getFieldResponses().stream().map(userFieldResponseDTO -> {
            SectionFieldTemplate fieldTemplate = fieldTemplateRepo.findById(userFieldResponseDTO.getFieldTemplateId())
                    .orElseThrow(() -> new RuntimeException("Field template not found"));

            return new UserFieldResponse(
                    fieldTemplate,
                    userFieldResponseDTO.getValue(),
                    sectionResponse
            );
        }).toList();

        sectionResponse.setFieldResponses(responses);
        return sectionResponseRepo.save(sectionResponse);
    }

    public UserSectionResponse updateSection(UUID id, SubmitUserSectionRequest request) {
        UserSectionResponse sectionResponse = sectionResponseRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Section response not found"));

        sectionResponse.getFieldResponses().clear();
        request.getFieldResponses().forEach(userFieldResponseDTO -> {
            SectionFieldTemplate fieldTemplate = fieldTemplateRepo.findById(userFieldResponseDTO.getFieldTemplateId())
                    .orElseThrow(() -> new RuntimeException("Field template not found"));
            UserFieldResponse response = new UserFieldResponse(
                    fieldTemplate,
                    userFieldResponseDTO.getValue(),
                    sectionResponse
            );
            sectionResponse.getFieldResponses().add(response);
        });

        return sectionResponseRepo.save(sectionResponse);
    }
}

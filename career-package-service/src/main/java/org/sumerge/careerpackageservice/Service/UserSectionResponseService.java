
package org.sumerge.careerpackageservice.Service;

import org.sumerge.careerpackageservice.Dto.Request.SubmitSectionResponseRequest;
import org.sumerge.careerpackageservice.Entity.*;
import org.sumerge.careerpackageservice.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final UserFieldResponseRepository fieldResponseRepo;

    public UserSectionResponseService(UserCareerPackageRepository careerPackageRepo, SectionTemplateRepository sectionTemplateRepo, SectionFieldTemplateRepository fieldTemplateRepo, UserSectionResponseRepository sectionResponseRepo, UserFieldResponseRepository fieldResponseRepo) {
        this.careerPackageRepo = careerPackageRepo;
        this.sectionTemplateRepo = sectionTemplateRepo;
        this.fieldTemplateRepo = fieldTemplateRepo;
        this.sectionResponseRepo = sectionResponseRepo;
        this.fieldResponseRepo = fieldResponseRepo;
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

    public UserSectionResponse submitSection(SubmitSectionResponseRequest request) {
        UserCareerPackage pkg = careerPackageRepo.findById(request.getUserCareerPackageId())
                .orElseThrow(() -> new RuntimeException("Career package not found"));

        SectionTemplate sectionTemplate = sectionTemplateRepo.findById(request.getSectionTemplateId())
                .orElseThrow(() -> new RuntimeException("Section template not found"));

        UserSectionResponse sectionResponse = new UserSectionResponse();
        sectionResponse.setUserCareerPackage(pkg);
        sectionResponse.setSectionTemplate(sectionTemplate);

        List<UserFieldResponse> responses = request.getFieldResponses().stream().map(dto -> {
            SectionFieldTemplate fieldTemplate = fieldTemplateRepo.findById(dto.getFieldTemplateId())
                    .orElseThrow(() -> new RuntimeException("Field template not found"));
            UserFieldResponse response = new UserFieldResponse();
            response.setFieldTemplate(fieldTemplate);
            response.setValue(dto.getValue());
            response.setSectionResponse(sectionResponse);
            return response;
        }).toList();

        sectionResponse.setFieldResponses(responses);
        return sectionResponseRepo.save(sectionResponse);
    }

    public UserSectionResponse updateSection(UUID id, SubmitSectionResponseRequest request) {
        UserSectionResponse sectionResponse = sectionResponseRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Section response not found"));

        sectionResponse.getFieldResponses().clear();
        request.getFieldResponses().forEach(dto -> {
            SectionFieldTemplate fieldTemplate = fieldTemplateRepo.findById(dto.getFieldTemplateId())
                    .orElseThrow(() -> new RuntimeException("Field template not found"));
            UserFieldResponse response = new UserFieldResponse();
            response.setFieldTemplate(fieldTemplate);
            response.setValue(dto.getValue());
            response.setSectionResponse(sectionResponse);
            sectionResponse.getFieldResponses().add(response);
        });

        return sectionResponseRepo.save(sectionResponse);
    }
}

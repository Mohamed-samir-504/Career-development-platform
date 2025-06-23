
package org.sumerge.careerpackageservice.Service;

import org.sumerge.careerpackageservice.Dto.Request.SubmitUserFieldRequest;
import org.sumerge.careerpackageservice.Entity.SectionFieldTemplate;
import org.sumerge.careerpackageservice.Entity.UserFieldResponse;
import org.sumerge.careerpackageservice.Entity.UserSectionResponse;
import org.sumerge.careerpackageservice.Repository.SectionFieldTemplateRepository;
import org.sumerge.careerpackageservice.Repository.UserFieldResponseRepository;
import org.springframework.stereotype.Service;
import org.sumerge.careerpackageservice.Repository.UserSectionResponseRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserFieldResponseService {


    private final UserSectionResponseRepository sectionResponseRepo;
    private final SectionFieldTemplateRepository fieldTemplateRepo;
    private final UserFieldResponseRepository fieldResponseRepo;

    public UserFieldResponseService(UserSectionResponseRepository sectionResponseRepo, SectionFieldTemplateRepository fieldTemplateRepo, UserFieldResponseRepository fieldResponseRepo) {
        this.sectionResponseRepo = sectionResponseRepo;
        this.fieldTemplateRepo = fieldTemplateRepo;
        this.fieldResponseRepo = fieldResponseRepo;
    }

    public List<UserFieldResponse> getAll() {
        return fieldResponseRepo.findAll();
    }

    public Optional<UserFieldResponse> getById(UUID id) {
        return fieldResponseRepo.findById(id);
    }

    public UserFieldResponse create(UserFieldResponse obj) {
        return fieldResponseRepo.save(obj);
    }

    public void delete(UUID id) {
        fieldResponseRepo.deleteById(id);
    }

    public UserFieldResponse submitField(SubmitUserFieldRequest request) {
        UserSectionResponse sectionResponse = sectionResponseRepo.findById(request.getSectionResponseId())
                .orElseThrow(() -> new RuntimeException("Section response not found"));

        SectionFieldTemplate fieldTemplate = fieldTemplateRepo.findById(request.getFieldTemplateId())
                .orElseThrow(() -> new RuntimeException("Field template not found"));

        UserFieldResponse response = new UserFieldResponse();
        response.setSectionResponse(sectionResponse);
        response.setFieldTemplate(fieldTemplate);
        response.setValue(request.getValue());

        return fieldResponseRepo.save(response);
    }

    public UserFieldResponse updateField(UUID id, String value) {
        UserFieldResponse response = fieldResponseRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Field response not found"));
        response.setValue(value);
        return fieldResponseRepo.save(response);
    }
}

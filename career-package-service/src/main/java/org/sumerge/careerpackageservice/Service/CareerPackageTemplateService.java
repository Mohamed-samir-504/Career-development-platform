
package org.sumerge.careerpackageservice.Service;

import org.sumerge.careerpackageservice.Dto.Request.CareerPackageEditRequest;
import org.sumerge.careerpackageservice.Dto.SectionFieldTemplateDTO;
import org.sumerge.careerpackageservice.Dto.SectionTemplateDTO;
import org.sumerge.careerpackageservice.Entity.CareerPackageTemplate;
import org.sumerge.careerpackageservice.Entity.SectionFieldTemplate;
import org.sumerge.careerpackageservice.Entity.SectionTemplate;
import org.sumerge.careerpackageservice.Mapper.UserCareerPackageMapper;
import org.sumerge.careerpackageservice.Repository.CareerPackageTemplateRepository;
import org.springframework.stereotype.Service;
import org.sumerge.careerpackageservice.Repository.SectionFieldTemplateRepository;
import org.sumerge.careerpackageservice.Repository.SectionTemplateRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CareerPackageTemplateService {

    private final SectionTemplateService sectionTemplateService;
    private final SectionFieldTemplateService sectionFieldTemplateService;

    private final CareerPackageTemplateRepository careerPackageTemplateRepository;
    private final UserCareerPackageMapper mapper;

    public CareerPackageTemplateService(CareerPackageTemplateRepository careerPackageTemplateRepository, SectionTemplateRepository sectionTemplateRepository, SectionFieldTemplateRepository sectionFieldTemplateRepository, SectionTemplateService sectionTemplateService, SectionFieldTemplateService sectionFieldTemplateService, UserCareerPackageMapper mapper) {
        this.careerPackageTemplateRepository = careerPackageTemplateRepository;
        this.sectionTemplateService = sectionTemplateService;
        this.sectionFieldTemplateService = sectionFieldTemplateService;
        this.mapper = mapper;
    }

    public List<CareerPackageTemplate> getAll() {
        return careerPackageTemplateRepository.findAll();
    }

    public Optional<CareerPackageTemplate> getById(UUID id) {
        return careerPackageTemplateRepository.findById(id);
    }

    public CareerPackageTemplate create(CareerPackageTemplate obj) {
        return careerPackageTemplateRepository.save(obj);
    }

    public void delete(UUID id) {
        careerPackageTemplateRepository.deleteById(id);
    }

    public void syncChanges(UUID packageId, CareerPackageEditRequest request) {
        CareerPackageTemplate pkg = careerPackageTemplateRepository.findById(packageId)
                .orElseThrow(() -> new RuntimeException("Package not found"));

        // === 1. Update Package Metadata ===
        if (request.getTitle() != null) pkg.setTitle(request.getTitle());
        if (request.getDescription() != null) pkg.setDescription(request.getDescription());

        // === 2. Delete Sections ===
        if (request.getDeletedSectionIds() != null) {
            request.getDeletedSectionIds().forEach(sectionTemplateService::delete);
        }

        // === 3. Delete Fields ===
        if (request.getDeletedFieldIds() != null) {
            request.getDeletedFieldIds().forEach(sectionFieldTemplateService::delete);
        }

        // === 4. Update Existing Sections ===
        if (request.getUpdatedSections() != null) {
            for (SectionTemplateDTO dto : request.getUpdatedSections()) {
                SectionTemplate section = sectionTemplateService.getById(dto.getId())
                        .orElseThrow(() -> new RuntimeException("Section not found"));

                section.setTitle(dto.getTitle());
                section.setType(dto.getType());
                section.setInstructions(dto.getInstructions());
                section.setRequirements(dto.getRequirements());

                sectionTemplateService.create(section); // save update
            }
        }

        // === 5. Update Existing Fields ===
        if (request.getUpdatedFields() != null) {
            for (SectionFieldTemplateDTO dto : request.getUpdatedFields()) {
                SectionFieldTemplate field = sectionFieldTemplateService.getById(dto.getId())
                        .orElseThrow(() -> new RuntimeException("Field not found"));

                field.setLabel(dto.getLabel());
                field.setFieldKey(dto.getFieldKey());
                field.setFieldType(dto.getFieldType());
                field.setRequired(dto.isRequired());

                sectionFieldTemplateService.create(field);
            }
        }

        // === 6. Add New Sections ===
        if (request.getNewSections() != null) {
            for (SectionTemplateDTO dto : request.getNewSections()) {
                SectionTemplate section = mapper.toEntity(dto);
                pkg.getSections().add(section);
                sectionTemplateService.create(section);

            }
        }

        // === 7. Add New Fields ===
        if (request.getNewFields() != null) {
            for (SectionFieldTemplateDTO dto : request.getNewFields()) {
                SectionTemplate section = sectionTemplateService.getById(dto.getSectionTemplateId())
                        .orElseThrow(() -> new RuntimeException("Section not found for field"));

                SectionFieldTemplate field = mapper.toEntity(dto);
                section.getFields().add(field);
                sectionTemplateService.create(section);
            }
        }

        // === 8. Save Package ===
        careerPackageTemplateRepository.save(pkg);
    }


}


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

        // === 3. Update Existing Sections ===
        if (request.getUpdatedSections() != null) {
            for (SectionTemplateDTO dto : request.getUpdatedSections()) {
                SectionTemplate section = sectionTemplateService.getById(dto.getId())
                        .orElseThrow(() -> new RuntimeException("Section not found"));

                section.setTitle(dto.getTitle());
                section.setType(dto.getType());
                section.setInstructions(dto.getInstructions());
                section.setRequirements(dto.getRequirements());

                // Clear old fields and replace with new
                section.getFields().clear();
                for (SectionFieldTemplateDTO fieldDto : dto.getFields()) {
                    SectionFieldTemplate field = mapper.toEntity(fieldDto);
                    section.getFields().add(field);
                }

                sectionTemplateService.create(section); // save updates
            }
        }

        // === 4. Add New Sections ===
        if (request.getNewSections() != null) {
            for (SectionTemplateDTO dto : request.getNewSections()) {
                SectionTemplate section = mapper.toEntity(dto);
                pkg.getSections().add(section);
            }
        }

        // === 5. Save Entire Package ===
        careerPackageTemplateRepository.save(pkg);
    }

}


package org.sumerge.careerpackageservice.Service;

import org.sumerge.careerpackageservice.Dto.CareerPackageTemplateDTO;
import org.sumerge.careerpackageservice.Dto.Request.CareerPackageEditRequest;
import org.sumerge.careerpackageservice.Dto.Request.CreateCareerPackageRequest;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CareerPackageTemplateService {

    private final CareerPackageTemplateRepository careerPackageTemplateRepository;
    private final SectionTemplateRepository sectionTemplateRepository;
    private final SectionFieldTemplateRepository sectionFieldTemplateRepository;
    private final UserCareerPackageMapper mapper;

    public CareerPackageTemplateService(CareerPackageTemplateRepository careerPackageTemplateRepository, SectionTemplateRepository sectionTemplateRepository, SectionFieldTemplateRepository sectionFieldTemplateRepository, UserCareerPackageMapper mapper) {
        this.careerPackageTemplateRepository = careerPackageTemplateRepository;
        this.sectionTemplateRepository = sectionTemplateRepository;
        this.sectionFieldTemplateRepository = sectionFieldTemplateRepository;
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

    public CareerPackageTemplateDTO createNewPackage(CreateCareerPackageRequest request) {
        CareerPackageTemplate newPackage = new CareerPackageTemplate();
        newPackage.setTitle(request.getTitle());
        newPackage.setDescription(request.getDescription());
        newPackage.setSections(new ArrayList<>());

        CareerPackageTemplate saved = careerPackageTemplateRepository.save(newPackage);

        return mapper.toDto(saved);
    }

    public void syncChanges(UUID packageId, CareerPackageEditRequest request) {
        CareerPackageTemplate pkg = careerPackageTemplateRepository.findById(packageId)
                .orElseThrow(() -> new RuntimeException("Package not found"));

        // === 1. Update Package Metadata ===
        if (request.getTitle() != null) pkg.setTitle(request.getTitle());
        if (request.getDescription() != null) pkg.setDescription(request.getDescription());

        // === 2. Delete Sections ===
        if (!request.getDeletedSectionIds().isEmpty()) {
            request.getDeletedSectionIds().forEach(sectionTemplateRepository::deleteById);
        }

        // === 3. Delete Fields ===
        if (!request.getDeletedFieldIds().isEmpty()) {
            request.getDeletedFieldIds().forEach(sectionFieldTemplateRepository::deleteById);
        }

        // === 4. Update Existing Sections ===
        if (!request.getUpdatedSections().isEmpty()) {
            for (SectionTemplateDTO dto : request.getUpdatedSections()) {
                SectionTemplate section = sectionTemplateRepository.findById(dto.getId())
                        .orElseThrow(() -> new RuntimeException("section not found"));

                section.setTitle(dto.getTitle());
                section.setType(dto.getType());
                section.setInstructions(dto.getInstructions());
                section.setRequirements(dto.getRequirements());

                sectionTemplateRepository.save(section); // save update
            }
        }

        // === 5. Update Existing Fields ===
        if (!request.getUpdatedFields().isEmpty()) {
            for (SectionFieldTemplateDTO dto : request.getUpdatedFields()) {
                SectionFieldTemplate field = sectionFieldTemplateRepository.findById(dto.getId())
                        .orElseThrow(() -> new RuntimeException("Field not found"));

                field.setLabel(dto.getLabel());
                field.setFieldKey(dto.getFieldKey());
                field.setFieldType(dto.getFieldType());
                field.setRequired(dto.isRequired());

                sectionFieldTemplateRepository.save(field);
            }
        }

        // === 6. Add New Sections ===
        if (!request.getNewSections().isEmpty()) {
            for (SectionTemplateDTO dto : request.getNewSections()) {
                SectionTemplate section = mapper.toEntity(dto);
                pkg.getSections().add(section);
                sectionTemplateRepository.save(section);

            }
        }

        // === 7. Add New Fields ===
        if (!request.getNewFields().isEmpty()) {
            for (SectionFieldTemplateDTO dto : request.getNewFields()) {
                if(dto.getSectionTemplateId()!= null){
                    SectionTemplate section = sectionTemplateRepository.findById(dto.getSectionTemplateId())
                            .orElseThrow(() -> new RuntimeException("Section not found for field"));

                    SectionFieldTemplate field = mapper.toEntity(dto);
                    section.getFields().add(field);
                    sectionTemplateRepository.save(section);
                }

            }
        }

        // === 8. Save Package ===
        careerPackageTemplateRepository.save(pkg);
    }


}

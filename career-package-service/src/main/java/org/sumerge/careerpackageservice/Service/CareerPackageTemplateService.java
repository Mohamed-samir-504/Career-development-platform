
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

import java.util.*;

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
        CareerPackageTemplate newPackage = new CareerPackageTemplate(
                request.getTitle(),
                request.getDescription(),
                new ArrayList<>()
        );

        CareerPackageTemplate saved = careerPackageTemplateRepository.save(newPackage);

        return mapper.toDto(saved);
    }

    public void syncChanges(UUID packageId, CareerPackageEditRequest request) {
        CareerPackageTemplate userCareerPackage = careerPackageTemplateRepository.findById(packageId)
                .orElseThrow(() -> new RuntimeException("Package not found"));

        // === 1. Update Package Metadata ===
        if (request.getTitle() != null) userCareerPackage.setTitle(request.getTitle());
        if (request.getDescription() != null) userCareerPackage.setDescription(request.getDescription());

        // === 2. Delete Sections ===
        request.getDeletedSectionIds()
                .forEach(sectionTemplateRepository::deleteById);

        // === 3. Delete Fields ===
        request.getDeletedFieldIds()
                .forEach(sectionFieldTemplateRepository::deleteById);

        // === 4. Update Existing Sections ===
        request.getUpdatedSections().stream()
                .map(sectionTemplateDTO -> new AbstractMap.SimpleEntry<>(
                        sectionTemplateDTO,
                        sectionTemplateRepository.findById(sectionTemplateDTO.getId())
                                .orElseThrow(() -> new RuntimeException("Section not found"))
                ))
                .forEach(entry -> {
                    SectionTemplateDTO sectionTemplateDTO = entry.getKey();
                    SectionTemplate section = entry.getValue();

                    section.setTitle(sectionTemplateDTO.getTitle());
                    section.setType(sectionTemplateDTO.getType());
                    section.setInstructions(sectionTemplateDTO.getInstructions());


                    sectionTemplateRepository.save(section);
                });

        // === 5. Update Existing Fields ===
        request.getUpdatedFields().stream()
                .map(sectionFieldTemplateDTO -> new AbstractMap.SimpleEntry<>(
                        sectionFieldTemplateDTO,
                        sectionFieldTemplateRepository.findById(sectionFieldTemplateDTO.getId())
                                .orElseThrow(() -> new RuntimeException("Field not found"))
                ))
                .forEach(entry -> {
                    SectionFieldTemplateDTO sectionFieldTemplateDTO = entry.getKey();
                    SectionFieldTemplate field = entry.getValue();

                    field.setLabel(sectionFieldTemplateDTO.getLabel());
                    field.setFieldKey(sectionFieldTemplateDTO.getFieldKey());
                    field.setFieldType(sectionFieldTemplateDTO.getFieldType());
                    field.setRequired(sectionFieldTemplateDTO.isRequired());

                    sectionFieldTemplateRepository.save(field);
                });

        // === 6. Add New Sections ===
        request.getNewSections().stream()
                .map(mapper::toEntity)
                .forEach(section -> {
                    userCareerPackage.getSections().add(section);
                    sectionTemplateRepository.save(section);
                });

        // === 7. Add New Fields ===
        request.getNewFields().stream()
                .filter(sectionFieldTemplateDTO -> sectionFieldTemplateDTO.getSectionTemplateId() != null)
                .forEach(sectionFieldTemplateDTO -> {
                    SectionTemplate section = sectionTemplateRepository.findById(sectionFieldTemplateDTO.getSectionTemplateId())
                            .orElseThrow(() -> new RuntimeException("Section not found for field"));
                    SectionFieldTemplate field = mapper.toEntity(sectionFieldTemplateDTO);
                    section.getFields().add(field);
                    sectionTemplateRepository.save(section);
                });

        // === 8. Save Package ===
        careerPackageTemplateRepository.save(userCareerPackage);
    }



}

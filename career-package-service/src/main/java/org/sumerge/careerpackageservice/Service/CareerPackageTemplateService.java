
package org.sumerge.careerpackageservice.Service;

import jakarta.persistence.EntityNotFoundException;
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

    public List<CareerPackageTemplateDTO> getAll() {
        return mapper.toCareerPackageDtoList(careerPackageTemplateRepository.findAll());
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
                .orElseThrow(() -> new EntityNotFoundException("Package not found"));

        //package data
        if (request.getTitle() != null) userCareerPackage.setTitle(request.getTitle());
        if (request.getDescription() != null) userCareerPackage.setDescription(request.getDescription());


        request.getDeletedSectionIds()
                .forEach(sectionTemplateRepository::deleteById);


        request.getDeletedFieldIds()
                .forEach(sectionFieldTemplateRepository::deleteById);

        // update existing sections
        request.getUpdatedSections().stream()
                .map(sectionTemplateDTO -> new AbstractMap.SimpleEntry<>(
                        sectionTemplateDTO,
                        sectionTemplateRepository.findById(sectionTemplateDTO.getId())
                                .orElseThrow(() -> new EntityNotFoundException("Section not found"))
                ))
                .forEach(entry -> {
                    SectionTemplateDTO sectionTemplateDTO = entry.getKey();
                    SectionTemplate section = entry.getValue();

                    section.setTitle(sectionTemplateDTO.getTitle());
                    section.setType(sectionTemplateDTO.getType());
                    section.setInstructions(sectionTemplateDTO.getInstructions());


                    sectionTemplateRepository.save(section);
                });

        // update existing fields
        request.getUpdatedFields().stream()
                .map(sectionFieldTemplateDTO -> new AbstractMap.SimpleEntry<>(
                        sectionFieldTemplateDTO,
                        sectionFieldTemplateRepository.findById(sectionFieldTemplateDTO.getId())
                                .orElseThrow(() -> new EntityNotFoundException("Field not found"))
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


        request.getNewSections().stream()
                .map(mapper::toEntity)
                .forEach(section -> {
                    userCareerPackage.getSections().add(section);
                    sectionTemplateRepository.save(section);
                });

        request.getNewFields().stream()
                .filter(sectionFieldTemplateDTO -> sectionFieldTemplateDTO.getSectionTemplateId() != null)
                .forEach(sectionFieldTemplateDTO -> {
                    SectionTemplate section = sectionTemplateRepository.findById(sectionFieldTemplateDTO.getSectionTemplateId())
                            .orElseThrow(() -> new EntityNotFoundException("Section not found for field"));
                    SectionFieldTemplate field = mapper.toEntity(sectionFieldTemplateDTO);
                    section.getFields().add(field);
                    sectionTemplateRepository.save(section);
                });

        careerPackageTemplateRepository.save(userCareerPackage);
    }



}

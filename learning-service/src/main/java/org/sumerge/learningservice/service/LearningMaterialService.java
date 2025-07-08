package org.sumerge.learningservice.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.sumerge.learningservice.dto.template.LearningMaterialTemplateDTO;
import org.sumerge.learningservice.dto.template.LearningSectionTemplateDTO;
import org.sumerge.learningservice.entity.LearningMaterialTemplate;
import org.sumerge.learningservice.entity.LearningSectionTemplate;
import org.sumerge.learningservice.mapper.LearningMaterialMapper;
import org.sumerge.learningservice.repository.LearningMaterialTemplateRepository;
import org.sumerge.learningservice.repository.LearningSectionResponseRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LearningMaterialService {

    private final LearningMaterialTemplateRepository templateRepository;
    private final LearningMaterialMapper mapper;
    private final LearningSectionResponseRepository learningSectionResponseRepository;

    public LearningMaterialTemplateDTO createTemplate(LearningMaterialTemplateDTO dto) {
        LearningMaterialTemplate template = mapper.toEntity(dto);

        if (template.getSections() != null) {
            for (LearningSectionTemplate section : template.getSections()) {
                section.setLearningMaterialTemplate(template);
            }
        }

        return mapper.toDto(templateRepository.save(template));
    }

    public List<LearningMaterialTemplateDTO> getAllTemplates() {
        return templateRepository.findAll()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public LearningMaterialTemplateDTO getTemplateById(UUID id) {
        LearningMaterialTemplate template = templateRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Template not found"));
        return mapper.toDto(template);
    }


    public List<LearningMaterialTemplateDTO> getTemplateByCareerPackageId(UUID careerPackageId) {
        return templateRepository.findByCareerPackageId(careerPackageId)
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public void deleteById(UUID id){
        templateRepository.deleteById(id);
    }

    public void update(UUID id, LearningMaterialTemplateDTO dto) {
        LearningMaterialTemplate existing = templateRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Template not found with id: " + id));

        existing.setTitle(dto.getTitle());
        existing.setPoints(dto.getPoints());
        existing.setDescription(dto.getDescription());
        existing.setCareerPackageId(dto.getCareerPackageId());

        Map<UUID, LearningSectionTemplate> existingMap = existing.getSections().stream()
                .collect(Collectors.toMap(LearningSectionTemplate::getId, s -> s));

        List<LearningSectionTemplate> patchedSections = new ArrayList<>();
        List<LearningSectionTemplate> newSections = new ArrayList<>();
        Set<UUID> incomingIds = new HashSet<>();

        for (LearningSectionTemplateDTO sectionDTO : dto.getSections()) {
            if (sectionDTO.getId() != null && existingMap.containsKey(sectionDTO.getId())) {

                LearningSectionTemplate section = existingMap.get(sectionDTO.getId());
                section.setTitle(sectionDTO.getTitle());
                section.setType(sectionDTO.getType());
                section.setInstructions(sectionDTO.getInstructions());
                section.setContent(sectionDTO.getContent());
                section.setRequiresSubmission(sectionDTO.isRequiresSubmission());
                section.setAttachmentId(sectionDTO.getAttachmentId());
                patchedSections.add(section);
                incomingIds.add(sectionDTO.getId());
            } else {
                LearningSectionTemplate newSection = new LearningSectionTemplate();
                newSection.setTitle(sectionDTO.getTitle());
                newSection.setType(sectionDTO.getType());
                newSection.setInstructions(sectionDTO.getInstructions());
                newSection.setContent(sectionDTO.getContent());
                newSection.setRequiresSubmission(sectionDTO.isRequiresSubmission());
                newSection.setAttachmentId(sectionDTO.getAttachmentId());
                newSection.setLearningMaterialTemplate(existing);
                newSections.add(newSection);
            }
        }
        List<LearningSectionTemplate> toRemove = existing.getSections().stream()
                .filter(s -> !incomingIds.contains(s.getId()))
                .filter(s -> !learningSectionResponseRepository.existsBySectionTemplateId(s.getId()))
                .toList();

        existing.getSections().removeAll(toRemove);
        existing.getSections().clear(); // Clear and reassign to prevent Hibernate orphan issues
        existing.getSections().addAll(patchedSections);
        existing.getSections().addAll(newSections);

        templateRepository.save(existing);
    }





}


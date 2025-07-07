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

    @Transactional
    public void update(UUID id, LearningMaterialTemplateDTO dto) {
// TODO: implement update method
    }



}


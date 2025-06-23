package org.sumerge.learningservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.sumerge.learningservice.dto.submission.LearningFieldResponseDTO;
import org.sumerge.learningservice.dto.submission.LearningSectionResponseDTO;
import org.sumerge.learningservice.dto.submission.LearningSubmissionDTO;
import org.sumerge.learningservice.dto.template.LearningFieldTemplateDTO;
import org.sumerge.learningservice.dto.template.LearningMaterialTemplateDTO;
import org.sumerge.learningservice.dto.template.LearningSectionTemplateDTO;
import org.sumerge.learningservice.entity.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LearningMaterialMapper {
// Template to DTO
    @Mapping(source = "sections", target = "sections")
    LearningMaterialTemplateDTO toDto(LearningMaterialTemplate template);

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "title", target = "title"),
            @Mapping(source = "type", target = "type"),
            @Mapping(source = "instructions", target = "instructions"),
            @Mapping(source = "content", target = "content"),
            @Mapping(source = "requiresSubmission", target = "requiresSubmission"),
            @Mapping(source = "fields", target = "fields")
    })
    LearningSectionTemplateDTO toDto(LearningSectionTemplate section);

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "label", target = "label"),
            @Mapping(source = "fieldKey", target = "fieldKey"),
            @Mapping(source = "fieldType", target = "fieldType"),
            @Mapping(source = "required", target = "required")
    })
    LearningFieldTemplateDTO toDto(LearningFieldTemplate field);

    // Response DTOs
    @Mappings({
            @Mapping(source = "sectionTemplate.id", target = "sectionTemplateId"),
            @Mapping(source = "fieldResponses", target = "fieldResponses")
    })
    LearningSectionResponseDTO toDto(LearningSectionResponse response);

    @Mappings({
            @Mapping(source = "fieldTemplate.id", target = "fieldTemplateId"),
            @Mapping(source = "value", target = "value")
    })
    LearningFieldResponseDTO toDto(LearningFieldResponse response);

    // List mappers
    List<LearningSectionTemplateDTO> toSectionTemplateDtoList(List<LearningSectionTemplate> sections);

    List<LearningFieldTemplateDTO> toFieldTemplateDtoList(List<LearningFieldTemplate> fields);

    List<LearningSectionResponseDTO> toSectionResponseDtoList(List<LearningSectionResponse> responses);

    List<LearningFieldResponseDTO> toFieldResponseDtoList(List<LearningFieldResponse> responses);

    // Reverse Mappings (DTO → Entity)
    LearningMaterialTemplate toEntity(LearningMaterialTemplateDTO dto);

    LearningSectionTemplate toEntity(LearningSectionTemplateDTO dto);

    LearningFieldTemplate toEntity(LearningFieldTemplateDTO dto);

    LearningSectionResponse toEntity(LearningSectionResponseDTO dto);

    LearningFieldResponse toEntity(LearningFieldResponseDTO dto);

    // Reverse List Mappings
    List<LearningSectionTemplate> toSectionTemplateEntityList(List<LearningSectionTemplateDTO> dtoList);

    List<LearningFieldTemplate> toFieldTemplateEntityList(List<LearningFieldTemplateDTO> dtoList);

    List<LearningSectionResponse> toSectionResponseEntityList(List<LearningSectionResponseDTO> dtoList);

    List<LearningFieldResponse> toFieldResponseEntityList(List<LearningFieldResponseDTO> dtoList);

    // Learning Submission
    @Mappings({
            @Mapping(source = "template.id", target = "templateId"),
            @Mapping(source = "sectionResponses", target = "sectionResponses")
    })
    LearningSubmissionDTO toDto(LearningSubmission submission);

    @Mapping(source = "templateId", target = "template.id")
    LearningSubmission toEntity(LearningSubmissionDTO dto);
    }
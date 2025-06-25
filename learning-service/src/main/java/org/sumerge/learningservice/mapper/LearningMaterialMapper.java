package org.sumerge.learningservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.sumerge.learningservice.dto.submission.LearningSectionResponseDTO;
import org.sumerge.learningservice.dto.submission.LearningSubmissionDTO;
import org.sumerge.learningservice.dto.template.LearningMaterialTemplateDTO;
import org.sumerge.learningservice.dto.template.LearningSectionTemplateDTO;
import org.sumerge.learningservice.entity.LearningMaterialTemplate;
import org.sumerge.learningservice.entity.LearningSectionResponse;
import org.sumerge.learningservice.entity.LearningSectionTemplate;
import org.sumerge.learningservice.entity.LearningSubmission;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LearningMaterialMapper {

    @Mapping(source = "sections", target = "sections")
    LearningMaterialTemplateDTO toDto(LearningMaterialTemplate template);

    @Mapping(source = "sections", target = "sections")
    LearningMaterialTemplate toEntity(LearningMaterialTemplateDTO dto);

    LearningSectionTemplateDTO toDto(LearningSectionTemplate section);

    LearningSectionTemplate toEntity(LearningSectionTemplateDTO dto);

    @Mappings({
            @Mapping(source = "sectionTemplate.id", target = "sectionTemplateId"),
            @Mapping(source = "userInput", target = "userInput"),
            @Mapping(source = "documentId", target = "documentId")
    })
    LearningSectionResponseDTO toDto(LearningSectionResponse response);

    @Mappings({
            @Mapping(source = "sectionTemplateId", target = "sectionTemplate.id"),
            @Mapping(source = "userInput", target = "userInput"),
            @Mapping(source = "documentId", target = "documentId")
    })
    LearningSectionResponse toEntity(LearningSectionResponseDTO dto);

    @Mappings({
            @Mapping(source = "template.id", target = "templateId"),
            @Mapping(source = "sectionResponses", target = "sectionResponses")
    })
    LearningSubmissionDTO toDto(LearningSubmission submission);

    @Mapping(source = "templateId", target = "template.id")
    LearningSubmission toEntity(LearningSubmissionDTO dto);

    // List Mappers
    List<LearningSectionTemplateDTO> toSectionTemplateDtoList(List<LearningSectionTemplate> sections);
    List<LearningSectionTemplate> toSectionTemplateEntityList(List<LearningSectionTemplateDTO> dtos);

    List<LearningSectionResponseDTO> toSectionResponseDtoList(List<LearningSectionResponse> responses);
    List<LearningSectionResponse> toSectionResponseEntityList(List<LearningSectionResponseDTO> dtos);
}

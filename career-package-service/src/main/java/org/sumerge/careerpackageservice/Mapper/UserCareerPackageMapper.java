package org.sumerge.careerpackageservice.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.sumerge.careerpackageservice.Dto.*;
import org.sumerge.careerpackageservice.Entity.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserCareerPackageMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "template", target = "template")
    @Mapping(source = "sectionResponses", target = "sectionResponses")
    UserCareerPackageDTO toDto(UserCareerPackage entity);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "title", target = "title")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "sections", target = "sections")
    CareerPackageTemplateDTO toDto(CareerPackageTemplate template);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "title", target = "title")
    @Mapping(source = "type", target = "type")
    @Mapping(source = "instructions", target = "instructions")
    @Mapping(source = "requirements", target = "requirements")
    @Mapping(source = "fields", target = "fields")
    SectionTemplateDTO toDto(SectionTemplate section);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "label", target = "label")
    @Mapping(source = "fieldKey", target = "fieldKey")
    @Mapping(source = "fieldType", target = "fieldType")
    @Mapping(source = "required", target = "required")
    SectionFieldTemplateDTO toDto(SectionFieldTemplate field);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "sectionTemplate.id", target = "sectionTemplateId")
    @Mapping(source = "fieldResponses", target = "fieldResponses")
    UserSectionResponseDTO toDto(UserSectionResponse response);

    @Mapping(source = "fieldTemplate.id", target = "fieldTemplateId")
    @Mapping(source = "value", target = "value")
    UserFieldResponseDTO toDto(UserFieldResponse response);

    List<SectionTemplateDTO> toSectionDtoList(List<SectionTemplate> sections);

    List<SectionFieldTemplateDTO> toFieldDtoList(List<SectionFieldTemplate> fields);

    List<UserSectionResponseDTO> toSectionResponseDtoList(List<UserSectionResponse> responses);

    List<UserFieldResponseDTO> toFieldResponseDtoList(List<UserFieldResponse> responses);
}

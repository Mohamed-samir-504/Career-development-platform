package org.sumerge.careerpackageservice.Mapper;

import org.sumerge.careerpackageservice.Dto.*;
import org.sumerge.careerpackageservice.Entity.*;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserCareerPackageMapper {
    UserCareerPackageDTO toDto(UserCareerPackage entity);
    CareerPackageTemplateDTO toDto(CareerPackageTemplate template);
    SectionTemplateDTO toDto(SectionTemplate section);
    SectionFieldTemplateDTO toDto(SectionFieldTemplate field);
    UserSectionResponseDTO toDto(UserSectionResponse response);
    UserFieldResponseDTO toDto(UserFieldResponse response);

    List<SectionTemplateDTO> toSectionDtoList(List<SectionTemplate> sections);
    List<SectionFieldTemplateDTO> toFieldDtoList(List<SectionFieldTemplate> fields);
    List<UserSectionResponseDTO> toSectionResponseDtoList(List<UserSectionResponse> responses);
    List<UserFieldResponseDTO> toFieldResponseDtoList(List<UserFieldResponse> responses);
}
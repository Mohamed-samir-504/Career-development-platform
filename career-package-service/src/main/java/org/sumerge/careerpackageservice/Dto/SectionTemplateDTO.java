package org.sumerge.careerpackageservice.Dto;

import lombok.Data;
import org.sumerge.careerpackageservice.Enums.SectionType;

import java.util.List;
import java.util.UUID;

@Data
public class SectionTemplateDTO {
    private UUID id;
    private String title;
    private SectionType type;
    private String instructions;
    private String requirements;
    private List<SectionFieldTemplateDTO> fields;
}
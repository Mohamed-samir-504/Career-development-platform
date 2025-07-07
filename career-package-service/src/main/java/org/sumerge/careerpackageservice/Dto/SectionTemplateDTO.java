package org.sumerge.careerpackageservice.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.sumerge.careerpackageservice.Enums.SectionType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class SectionTemplateDTO {
    private UUID id;
    private String title;
    private SectionType type;
    private String instructions;
    private List<SectionFieldTemplateDTO> fields;
}
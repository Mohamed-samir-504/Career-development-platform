package org.sumerge.careerpackageservice.Dto;

import lombok.Data;
import java.util.List;
import java.util.UUID;

@Data
public class SectionTemplateDTO {
    private UUID id;
    private String title;
    private String type;
    private String instructions;
    private String requirements;
    private List<SectionFieldTemplateDTO> fields;
}
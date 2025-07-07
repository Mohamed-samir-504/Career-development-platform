package org.sumerge.careerpackageservice.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.UUID;

@Data
@AllArgsConstructor
public class SectionFieldTemplateDTO {
    private UUID id;
    private String label;
    private String fieldKey;
    private String fieldType;
    private boolean required;
    private UUID sectionTemplateId;
}
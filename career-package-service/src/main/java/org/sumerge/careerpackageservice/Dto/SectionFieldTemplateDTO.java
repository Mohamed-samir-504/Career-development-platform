package org.sumerge.careerpackageservice.Dto;

import lombok.Data;

@Data
public class SectionFieldTemplateDTO {
    private Long id;
    private String label;
    private String fieldKey;
    private String fieldType;
    private boolean required;
}
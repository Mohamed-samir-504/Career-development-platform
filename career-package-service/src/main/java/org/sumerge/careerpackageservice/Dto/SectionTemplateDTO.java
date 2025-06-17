package org.sumerge.careerpackageservice.Dto;

import lombok.Data;
import java.util.List;

@Data
public class SectionTemplateDTO {
    private Long id;
    private String title;
    private String type;
    private String instructions;
    private String requirements;
    private List<SectionFieldTemplateDTO> fields;
}
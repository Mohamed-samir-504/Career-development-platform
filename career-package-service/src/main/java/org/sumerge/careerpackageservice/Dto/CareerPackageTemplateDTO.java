package org.sumerge.careerpackageservice.Dto;

import lombok.Data;
import java.util.List;

@Data
public class CareerPackageTemplateDTO {
    private Long id;
    private String title;
    private String description;
    private List<SectionTemplateDTO> sections;
}
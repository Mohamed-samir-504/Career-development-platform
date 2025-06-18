package org.sumerge.careerpackageservice.Dto;

import lombok.Data;
import java.util.List;
import java.util.UUID;

@Data
public class CareerPackageTemplateDTO {
    private UUID id;
    private String title;
    private String description;
    private List<SectionTemplateDTO> sections;
}
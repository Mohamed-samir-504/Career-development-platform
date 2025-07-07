package org.sumerge.careerpackageservice.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CareerPackageTemplateDTO {
    private UUID id;
    private String title;
    private String description;
    private List<SectionTemplateDTO> sections;
}
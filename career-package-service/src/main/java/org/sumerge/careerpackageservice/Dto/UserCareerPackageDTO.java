package org.sumerge.careerpackageservice.Dto;

import lombok.Data;
import java.util.List;
import java.util.UUID;

@Data
public class UserCareerPackageDTO {
    private UUID id;
    private CareerPackageTemplateDTO template;
    private List<UserSectionResponseDTO> sectionResponses;
}
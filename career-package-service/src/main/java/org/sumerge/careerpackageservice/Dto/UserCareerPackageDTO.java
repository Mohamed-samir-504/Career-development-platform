package org.sumerge.careerpackageservice.Dto;

import lombok.Data;
import java.util.List;

@Data
public class UserCareerPackageDTO {
    private Long id;
    private CareerPackageTemplateDTO template;
    private List<UserSectionResponseDTO> sectionResponses;
}
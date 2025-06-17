package org.sumerge.careerpackageservice.Dto;

import lombok.Data;
import java.util.List;

@Data
public class UserSectionResponseDTO {
    private Long sectionTemplateId;
    private List<UserFieldResponseDTO> fieldResponses;
}
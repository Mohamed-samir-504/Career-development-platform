package org.sumerge.careerpackageservice.Dto;

import lombok.Data;
import java.util.List;
import java.util.UUID;

@Data
public class UserSectionResponseDTO {
    private UUID id;
    private UUID sectionTemplateId;
    private List<UserFieldResponseDTO> fieldResponses;
}
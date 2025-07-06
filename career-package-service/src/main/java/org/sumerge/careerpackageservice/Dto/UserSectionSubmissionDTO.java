package org.sumerge.careerpackageservice.Dto;

import lombok.Data;
import java.util.List;
import java.util.UUID;

@Data
public class UserSectionSubmissionDTO {
    private UUID id;
    private UUID sectionTemplateId;
    private List<UserFieldSubmissionDTO> fieldResponses;
}
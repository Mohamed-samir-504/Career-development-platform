package org.sumerge.careerpackageservice.Dto.Request;

import lombok.Data;
import org.sumerge.careerpackageservice.Dto.UserFieldSubmissionDTO;

import java.util.List;
import java.util.UUID;

@Data
public class SubmitUserSectionRequest {
    private UUID userCareerPackageId;
    private UUID sectionTemplateId;
    private List<UserFieldSubmissionDTO> fieldSubmissions;
    private List<UserFieldSubmissionDTO> newFieldSubmissions;
}

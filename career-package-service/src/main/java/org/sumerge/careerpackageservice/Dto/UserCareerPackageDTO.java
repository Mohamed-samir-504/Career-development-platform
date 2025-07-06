package org.sumerge.careerpackageservice.Dto;

import lombok.Data;
import org.sumerge.careerpackageservice.Enums.PackageStatus;

import java.util.List;
import java.util.UUID;

@Data
public class UserCareerPackageDTO {
    private UUID id;
    private UUID userId;
    private UUID reviewerId;
    private CareerPackageTemplateDTO template;
    private List<UserSectionSubmissionDTO> sectionResponses;
    private PackageStatus status;
    private String reviewerComment;
}
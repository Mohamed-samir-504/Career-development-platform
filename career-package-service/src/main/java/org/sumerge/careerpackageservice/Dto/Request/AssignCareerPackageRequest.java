package org.sumerge.careerpackageservice.Dto.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.sumerge.careerpackageservice.Enums.PackageStatus;

import java.util.UUID;

@Data
@AllArgsConstructor
public class AssignCareerPackageRequest {
    private UUID userId;
    private UUID reviewerId;
    private UUID templateId;
    private PackageStatus status;
}

package org.sumerge.careerpackageservice.Dto.Request;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class SubmitCareerPackageRequest {
    private UUID id;
    private String status;
    private List<SubmitSectionResponseRequest> sectionResponses;
}

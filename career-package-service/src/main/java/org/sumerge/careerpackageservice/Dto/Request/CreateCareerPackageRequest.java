package org.sumerge.careerpackageservice.Dto.Request;

import lombok.Data;

@Data
public class CreateCareerPackageRequest {
    private String title;
    private String description;
}

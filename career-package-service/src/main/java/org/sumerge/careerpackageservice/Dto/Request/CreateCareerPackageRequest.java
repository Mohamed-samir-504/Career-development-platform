package org.sumerge.careerpackageservice.Dto.Request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateCareerPackageRequest {
    private String title;
    private String description;
}

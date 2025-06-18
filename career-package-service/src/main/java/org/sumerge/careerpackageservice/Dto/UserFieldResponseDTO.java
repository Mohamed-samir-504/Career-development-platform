package org.sumerge.careerpackageservice.Dto;

import lombok.Data;
import java.util.UUID;

@Data
public class UserFieldResponseDTO {
    private UUID fieldTemplateId;
    private String value;
}
package org.sumerge.careerpackageservice.Dto;

import lombok.Data;
import java.util.UUID;

@Data
public class UserFieldSubmissionDTO {
    private UUID id;
    private UUID fieldTemplateId;
    private String value;
}
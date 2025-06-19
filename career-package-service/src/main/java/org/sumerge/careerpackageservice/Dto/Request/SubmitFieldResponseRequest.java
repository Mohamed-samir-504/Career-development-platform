package org.sumerge.careerpackageservice.Dto.Request;

import lombok.Data;

import java.util.UUID;

@Data
public class SubmitFieldResponseRequest {
    private UUID sectionResponseId;
    private UUID fieldTemplateId;
    private String value;
}

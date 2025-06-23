package org.sumerge.learningservice.dto.submission;

import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LearningFieldResponseDTO {
    private UUID fieldTemplateId;
    private String value;
    private String documentId;
}

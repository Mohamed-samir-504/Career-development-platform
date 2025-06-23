package org.sumerge.learningservice.dto.submission;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LearningSectionResponseDTO {
    private UUID sectionTemplateId;
    private List<LearningFieldResponseDTO> fieldResponses;
}

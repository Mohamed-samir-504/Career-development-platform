package org.sumerge.learningservice.dto.submission;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LearningSubmissionDTO {
    private UUID id;
    private UUID userId;
    private UUID templateId;
    private List<LearningSectionResponseDTO> sectionResponses;
}

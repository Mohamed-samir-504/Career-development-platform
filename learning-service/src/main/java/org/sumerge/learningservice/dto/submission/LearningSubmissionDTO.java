package org.sumerge.learningservice.dto.submission;

import lombok.*;
import org.sumerge.learningservice.enums.SubmissionStatus;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LearningSubmissionDTO {
    private UUID id;
    private UUID userId;
    private UUID managerId;
    private UUID templateId;
    private SubmissionStatus status;
    private List<LearningSectionResponseDTO> sectionResponses;
}

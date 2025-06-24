package org.sumerge.learningservice.dto.template;

import lombok.*;
import org.sumerge.learningservice.enums.SectionType;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LearningSectionTemplateDTO {
    private UUID id;
    private String title;
    private SectionType type;
    private String instructions;
    private String content;
    private boolean requiresSubmission;
    private String attachmentId;
}

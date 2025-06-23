package org.sumerge.learningservice.dto.template;

import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LearningFieldTemplateDTO {
    private UUID id;
    private String label;
    private String fieldKey;
    private String fieldType;
    private boolean required;
}

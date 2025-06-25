package org.sumerge.learningservice.dto.template;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LearningMaterialTemplateDTO {
    private UUID id;
    private String title;
    private String description;
    private List<LearningSectionTemplateDTO> sections;
}

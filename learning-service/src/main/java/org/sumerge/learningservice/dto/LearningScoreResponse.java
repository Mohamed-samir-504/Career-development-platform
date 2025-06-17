package org.sumerge.learningservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class LearningScoreResponse {
    private UUID userId;
    private int points;
}

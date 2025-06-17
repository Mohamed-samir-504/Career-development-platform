package org.sumerge.learningservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "learning_scores")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LearningScore {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private UUID userId;
    private Integer points;
    private LocalDateTime lastUpdated;

}

package org.sumerge.learningservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "learning_submissions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LearningSubmission {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private UUID userId;

    @ManyToOne
    @JoinColumn(name = "template_id")
    private LearningMaterialTemplate template;

    private LocalDateTime submittedAt;

    @OneToMany(mappedBy = "learningSubmission", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LearningSectionResponse> sectionResponses;

    @PrePersist
    public void onCreate() {
        submittedAt = LocalDateTime.now();
    }
}

package org.sumerge.learningservice.entity;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.sumerge.learningservice.enums.DocumentCategory;

@Document("documents")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LearningDocument {
    @Id
    private String id;

    private String fileName;
    private String contentType;
    private byte[] content;

    private String userId;

    @Enumerated(EnumType.STRING)
    private DocumentCategory category;

}


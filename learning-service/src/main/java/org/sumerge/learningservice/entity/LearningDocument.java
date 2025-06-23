package org.sumerge.learningservice.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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

    private String fieldKey;
    private String userId;
}

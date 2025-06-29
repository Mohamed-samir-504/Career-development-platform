package org.sumerge.learningservice.dto;

import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogWikiDTO {
    private UUID id;
    private String title;
    private String content;
    private String attachmentId;
}

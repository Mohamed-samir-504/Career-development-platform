package org.sumerge.learningservice.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogWikiDTO {
    private String title;
    private String content;
    private String attachmentId;
}

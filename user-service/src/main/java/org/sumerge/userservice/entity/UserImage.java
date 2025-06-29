package org.sumerge.userservice.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("user_images")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserImage {

    @Id
    private String id;

    private String fileName;
    private String contentType;
    private byte[] content;

    private String userId;

}

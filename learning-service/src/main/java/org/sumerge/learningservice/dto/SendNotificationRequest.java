package org.sumerge.learningservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class SendNotificationRequest {
    private UUID senderId;
    private UUID receiverId;
    private String message;
    private String type;
}

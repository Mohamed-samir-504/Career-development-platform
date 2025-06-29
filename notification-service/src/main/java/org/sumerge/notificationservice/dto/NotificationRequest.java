package org.sumerge.notificationservice.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class NotificationRequest {
    private UUID senderId;
    private UUID receiverId;
    private String message;
    private String type;
}


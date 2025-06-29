package org.sumerge.notificationservice.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class NotificationRequest {
    private UUID userId;
    private String message;
}


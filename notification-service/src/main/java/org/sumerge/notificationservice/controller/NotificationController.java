package org.sumerge.notificationservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.sumerge.notificationservice.dto.NotificationRequest;
import org.sumerge.notificationservice.entity.Notification;
import org.sumerge.notificationservice.service.NotificationService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping
    public ResponseEntity<Notification> create(@RequestBody NotificationRequest request) {
        return ResponseEntity.ok(notificationService.createNotification(request.getUserId(), request.getMessage()));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Notification>> getForUser(@PathVariable UUID userId) {
        return ResponseEntity.ok(notificationService.getUserNotifications(userId));
    }

    @PatchMapping("/{id}/read")
    public ResponseEntity<Void> markAsRead(@PathVariable UUID id) {
        notificationService.markAsRead(id);
        return ResponseEntity.noContent().build();
    }
}


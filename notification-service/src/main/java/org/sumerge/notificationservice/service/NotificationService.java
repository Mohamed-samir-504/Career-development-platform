package org.sumerge.notificationservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.sumerge.notificationservice.entity.Notification;
import org.sumerge.notificationservice.repository.NotificationRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepo;

    public Notification createNotification(UUID userId, String message) {
        return notificationRepo.save(Notification.builder()
                .userId(userId)
                .message(message)
                .build());
    }

    public List<Notification> getUserNotifications(UUID userId) {
        return notificationRepo.findByUserId(userId);
    }

    public void markAsRead(UUID notificationId) {
        notificationRepo.findById(notificationId).ifPresent(notification -> {
            notification.setRead(true);
            notificationRepo.save(notification);
        });
    }
}


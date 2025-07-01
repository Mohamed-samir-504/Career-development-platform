package org.sumerge.notificationservice.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.sumerge.notificationservice.dto.NotificationRequest;
import org.sumerge.notificationservice.entity.Notification;
import org.sumerge.notificationservice.mapper.NotificationMapper;
import org.sumerge.notificationservice.repository.NotificationRepository;

import java.util.List;
import java.util.UUID;

@Service

public class NotificationService {

    private final NotificationRepository notificationRepo;
    private final NotificationMapper notificationMapper;

    public NotificationService(NotificationRepository notificationRepo, NotificationMapper notificationMapper) {
        this.notificationRepo = notificationRepo;
        this.notificationMapper = notificationMapper;
    }

    public Notification createNotification(UUID receiverId, String message) {
        return notificationRepo.save(Notification.builder()
                .receiverId(receiverId)
                .message(message)
                .build());
    }

    public List<Notification> getUserNotifications(UUID receiverId) {
        return notificationRepo.findByReceiverId(receiverId);
    }

    public void markAsRead(UUID notificationId) {
        notificationRepo.findById(notificationId).ifPresent(notification -> {
            notification.setRead(true);
            notificationRepo.save(notification);
        });
    }

    public void delete(UUID notificationId) {
        notificationRepo.deleteById(notificationId);
    }

    @KafkaListener(topics = "career-package-submitted", groupId = "notification-group")
    public void consume(NotificationRequest request) {

        notificationRepo.save(notificationMapper.toEntity(request));
    }
}


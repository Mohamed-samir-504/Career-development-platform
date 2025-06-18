package org.sumerge.notificationservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.sumerge.notificationservice.entity.Notification;

import java.util.List;
import java.util.UUID;

public interface NotificationRepository extends JpaRepository<Notification, UUID> {
    List<Notification> findByUserId(UUID userId);
}


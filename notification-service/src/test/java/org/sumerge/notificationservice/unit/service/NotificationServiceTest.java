package org.sumerge.notificationservice.unit.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.sumerge.notificationservice.dto.NotificationRequest;
import org.sumerge.notificationservice.entity.Notification;
import org.sumerge.notificationservice.mapper.NotificationMapper;
import org.sumerge.notificationservice.repository.NotificationRepository;
import org.sumerge.notificationservice.service.NotificationService;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NotificationServiceTest {

    @Mock
    private NotificationRepository notificationRepo;

    @Mock
    private NotificationMapper notificationMapper;

    @InjectMocks
    private NotificationService notificationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createNotification_success() {
        UUID receiverId = UUID.randomUUID();
        String message = "Test notification";

        Notification expectedNotification = Notification.builder()
                .id(UUID.randomUUID())
                .receiverId(receiverId)
                .message(message)
                .build();

        when(notificationRepo.save(any(Notification.class))).thenReturn(expectedNotification);

        Notification result = notificationService.createNotification(receiverId, message);

        assertEquals(receiverId, result.getReceiverId());
        assertEquals(message, result.getMessage());
        verify(notificationRepo).save(any(Notification.class));
    }

    @Test
    void getUserNotifications_success() {
        UUID receiverId = UUID.randomUUID();

        List<Notification> mockList = List.of(
                Notification.builder().id(UUID.randomUUID()).receiverId(receiverId).message("Msg 1").build(),
                Notification.builder().id(UUID.randomUUID()).receiverId(receiverId).message("Msg 2").build()
        );

        when(notificationRepo.findByReceiverId(receiverId)).thenReturn(mockList);

        List<Notification> result = notificationService.getUserNotifications(receiverId);

        assertEquals(2, result.size());
        verify(notificationRepo).findByReceiverId(receiverId);
    }


    @Test
    void markAsRead_success() {
        UUID notificationId = UUID.randomUUID();

        Notification mockNotification = Notification.builder()
                .id(notificationId)
                .receiverId(UUID.randomUUID())
                .message("Read this")
                .read(false)
                .build();

        when(notificationRepo.findById(notificationId)).thenReturn(Optional.of(mockNotification));
        when(notificationRepo.save(any(Notification.class))).thenAnswer(invocation -> invocation.getArgument(0));

        notificationService.markAsRead(notificationId);

        assertTrue(mockNotification.isRead());
        verify(notificationRepo).save(mockNotification);
    }


    @Test
    void delete_success() {
        UUID id = UUID.randomUUID();

        notificationService.delete(id);

        verify(notificationRepo).deleteById(id);
    }


    @Test
    void consume_success() {
        NotificationRequest request = new NotificationRequest(
                UUID.randomUUID(),
                UUID.randomUUID(),
                "Kafka message",
                "Submission"
        );

        Notification mapped = Notification.builder()
                .id(UUID.randomUUID())
                .receiverId(request.getReceiverId())
                .message(request.getMessage())
                .read(false)
                .build();

        when(notificationMapper.toEntity(request)).thenReturn(mapped);
        when(notificationRepo.save(mapped)).thenReturn(mapped);

        notificationService.consume(request);

        verify(notificationRepo).save(mapped);
    }
}


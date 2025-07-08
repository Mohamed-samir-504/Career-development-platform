package org.sumerge.notificationservice.webLayer.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.sumerge.notificationservice.config.TestSecurityConfig;
import org.sumerge.notificationservice.controller.NotificationController;
import org.sumerge.notificationservice.dto.NotificationRequest;
import org.sumerge.notificationservice.entity.Notification;
import org.sumerge.notificationservice.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(NotificationController.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = {NotificationController.class, TestSecurityConfig.class})
class NotificationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private NotificationService notificationService;

    @Autowired
    private ObjectMapper objectMapper;

    private Notification mockNotification(UUID id, UUID receiverId, String message, boolean isRead) {
        return Notification.builder()
                .id(id)
                .receiverId(receiverId)
                .message(message)
                .read(isRead)
                .build();
    }

    @Test
    void createNotification_success() throws Exception {
        UUID receiverId = UUID.randomUUID();
        UUID senderId = UUID.randomUUID();
        NotificationRequest request = new NotificationRequest(senderId, receiverId,"Hello from test", "Submission");
        Notification notification = mockNotification(UUID.randomUUID(), receiverId, "Hello from test", false);

        when(notificationService.createNotification(receiverId, "Hello from test")).thenReturn(notification);

        mockMvc.perform(post("/api/notifications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.receiverId").value(receiverId.toString()))
                .andExpect(jsonPath("$.message").value("Hello from test"));
    }

    @Test
    void getNotifications_success() throws Exception {
        UUID userId = UUID.randomUUID();
        List<Notification> notifications = List.of(
                mockNotification(UUID.randomUUID(), userId, "Msg 1", false),
                mockNotification(UUID.randomUUID(), userId, "Msg 2", true)
        );

        when(notificationService.getUserNotifications(userId)).thenReturn(notifications);

        mockMvc.perform(get("/api/notifications/" + userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].message").value("Msg 1"));
    }

    @Test
    void markAsRead_success() throws Exception {
        UUID id = UUID.randomUUID();

        doNothing().when(notificationService).markAsRead(id);

        mockMvc.perform(patch("/api/notifications/" + id + "/read"))
                .andExpect(status().isNoContent());

        verify(notificationService, times(1)).markAsRead(id);
    }

    @Test
    void deleteNotification_success() throws Exception {
        UUID id = UUID.randomUUID();

        doNothing().when(notificationService).delete(id);

        mockMvc.perform(delete("/api/notifications/" + id))
                .andExpect(status().isNoContent());

        verify(notificationService, times(1)).delete(id);
    }
}


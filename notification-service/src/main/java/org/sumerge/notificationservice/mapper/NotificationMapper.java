package org.sumerge.notificationservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.sumerge.notificationservice.dto.NotificationRequest;
import org.sumerge.notificationservice.entity.Notification;

@Mapper(componentModel = "spring")
public interface NotificationMapper {

    @Mapping(source = "senderId", target = "senderId")
    @Mapping(source = "receiverId", target = "receiverId")
    @Mapping(source = "message", target = "message")
    @Mapping(source = "type", target = "type")
    NotificationRequest toDto(Notification notification);


    @Mapping(source = "senderId", target = "senderId")
    @Mapping(source = "receiverId", target = "receiverId")
    @Mapping(source = "message", target = "message")
    @Mapping(source = "type", target = "type")
    Notification toEntity(NotificationRequest notification);
}

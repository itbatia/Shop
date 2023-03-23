package com.itbatia.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.itbatia.user.model.Notification;
import com.itbatia.user.model.NotificationStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
public class NotificationDto {

    @JsonProperty("id")
    private Long id;
    @JsonProperty("title")
    private String title;
    @JsonProperty("created_at")
    private Date createdAt;
    @JsonProperty("content")
    private String content;
    @JsonProperty("user")
    private UserDto user;
    @JsonProperty("status")
    private NotificationStatus notificationStatus;

    public static NotificationDto fromNotification(Notification notification) {

        return NotificationDto.builder()
                .id(notification.getId())
                .title(notification.getTitle())
                .createdAt(notification.getCreatedAt())
                .content(notification.getContent())
                .user(UserDto.fromUser(notification.getUser()))
                .notificationStatus(notification.getNotificationStatus())
                .build();
    }

    public Notification toNotification() {

        Notification notification = new Notification();
        notification.setId(id);
        notification.setTitle(title);
        notification.setCreatedAt(createdAt);
        notification.setContent(content);
        notification.setUser(user.toUser());
        notification.setNotificationStatus(notificationStatus);

        return notification;
    }

    public static List<NotificationDto> fromNotifications(List<Notification> notifications) {
        return notifications.stream().map(NotificationDto::fromNotification).collect(Collectors.toList());
    }
}

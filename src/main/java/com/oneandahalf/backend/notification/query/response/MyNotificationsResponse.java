package com.oneandahalf.backend.notification.query.response;

import com.oneandahalf.backend.notification.domain.Notification;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;

public record MyNotificationsResponse(
        List<NotificationResponse> notifications,
        boolean existNewNotification
) {

    public static MyNotificationsResponse from(List<Notification> notifications) {
        List<NotificationResponse> list = notifications.stream()
                .map(NotificationResponse::from)
                .toList();
        boolean existNewNotification = notifications.stream()
                .anyMatch(it -> !it.isRead());
        return new MyNotificationsResponse(list, existNewNotification);
    }

    @Builder
    public record NotificationResponse(
            Long id,
            String content,
            String linkedURI,
            boolean read,
            LocalDateTime createdDate
    ) {
        public static NotificationResponse from(Notification notification) {
            return NotificationResponse.builder()
                    .id(notification.getId())
                    .content(notification.getContent())
                    .linkedURI(notification.getLinkedURI())
                    .read(notification.isRead())
                    .createdDate(notification.getCreatedDate())
                    .build();
        }
    }
}

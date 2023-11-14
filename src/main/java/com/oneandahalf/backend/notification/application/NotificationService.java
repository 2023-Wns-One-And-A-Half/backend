package com.oneandahalf.backend.notification.application;

import com.oneandahalf.backend.notification.domain.Notification;
import com.oneandahalf.backend.notification.domain.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public void read(Long memberId, Long notificationId) {
        Notification notification = notificationRepository.getByIdAndMemberId(notificationId, memberId);
        notification.read();
    }
}

package com.oneandahalf.backend.notification.domain;

import com.oneandahalf.backend.common.exception.NotFoundEntityException;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    default Notification getByIdAndMemberId(Long notificationId, Long memberId) {
        return findByIdAndMemberId(notificationId, memberId).orElseThrow(() ->
                new NotFoundEntityException("존재하지 않는 알림입니다.")
        );
    }
    
    Optional<Notification> findByIdAndMemberId(Long notificationId, Long memberId);
}

package com.oneandahalf.backend.notification.query.dao.support;


import com.oneandahalf.backend.notification.domain.Notification;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationQuerySupport extends JpaRepository<Notification, Long> {

    List<Notification> findAllByMemberId(Long memberId);
}

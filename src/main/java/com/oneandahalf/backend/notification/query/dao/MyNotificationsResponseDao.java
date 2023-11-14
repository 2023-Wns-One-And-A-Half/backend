package com.oneandahalf.backend.notification.query.dao;

import com.oneandahalf.backend.notification.query.dao.support.NotificationQuerySupport;
import com.oneandahalf.backend.notification.query.response.MyNotificationsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MyNotificationsResponseDao {

    private final NotificationQuerySupport notificationQuerySupport;

    public MyNotificationsResponse find(Long memberId) {
        return MyNotificationsResponse.from(notificationQuerySupport.findAllByMemberId(memberId));
    }
}

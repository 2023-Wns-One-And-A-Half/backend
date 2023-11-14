package com.oneandahalf.backend.notification.query;

import com.oneandahalf.backend.notification.query.dao.MyNotificationsResponseDao;
import com.oneandahalf.backend.notification.query.response.MyNotificationsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class NotificationQueryService {

    private final MyNotificationsResponseDao myNotificationResponseDao;

    public MyNotificationsResponse findMine(Long memberId) {
        return myNotificationResponseDao.find(memberId);
    }
}

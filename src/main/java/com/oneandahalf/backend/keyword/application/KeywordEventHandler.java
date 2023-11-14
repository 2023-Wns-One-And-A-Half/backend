package com.oneandahalf.backend.keyword.application;

import com.oneandahalf.backend.keyword.domain.Keyword;
import com.oneandahalf.backend.keyword.domain.KeywordRepository;
import com.oneandahalf.backend.notification.domain.Notification;
import com.oneandahalf.backend.notification.domain.NotificationRepository;
import com.oneandahalf.backend.product.domain.RegisterProductEvent;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class KeywordEventHandler {

    private static final String NOTIFICATION_CONTENT_FORMAT = """
            "%s" 키워드 관련 게시물이 올라왔어요.
            """.strip();

    private final KeywordRepository keywordRepository;
    private final NotificationRepository notificationRepository;

    @EventListener(RegisterProductEvent.class)
    public void notifyRegisteredProductContainsKeyword(RegisterProductEvent event) {
        List<Notification> notifications = keywordRepository.findAllByContainsContent(event.product().getName())
                .stream()
                .filter(it -> notMyProduct(event, it))
                .map(it -> generateNotification(it, event.product().getId()))
                .toList();
        notificationRepository.saveAll(notifications);
    }

    private boolean notMyProduct(RegisterProductEvent event, Keyword keyword) {
        return !keyword.getMember().getId().equals(event.product().getSeller().getId());
    }

    private Notification generateNotification(Keyword keyword, Long productId) {
        return Notification.builder()
                .member(keyword.getMember())
                .content(NOTIFICATION_CONTENT_FORMAT.formatted(keyword.getContent()))
                .linkedURI("/products/" + productId)
                .build();
    }
}

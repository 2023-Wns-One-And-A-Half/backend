package com.oneandahalf.backend.notification.presentation;

import com.oneandahalf.backend.member.presentation.support.Auth;
import com.oneandahalf.backend.notification.application.NotificationService;
import com.oneandahalf.backend.notification.query.NotificationQueryService;
import com.oneandahalf.backend.notification.query.response.MyNotificationsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/notifications")
@RestController
public class NotificationController {

    private final NotificationService notificationService;
    private final NotificationQueryService notificationQueryService;

    @GetMapping("/my")
    public ResponseEntity<MyNotificationsResponse> findMine(
            @Auth Long memberId
    ) {
        return ResponseEntity.ok(notificationQueryService.findMine(memberId));
    }

    @PostMapping("/read/{id}")
    public ResponseEntity<Void> read(
            @Auth Long memberId,
            @PathVariable("id") Long notificationId
    ) {
        notificationService.read(memberId, notificationId);
        return ResponseEntity.ok().build();
    }
}

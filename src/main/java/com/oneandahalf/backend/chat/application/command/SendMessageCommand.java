package com.oneandahalf.backend.chat.application.command;

import lombok.Builder;

@Builder
public record SendMessageCommand(
        Long senderId,
        Long chatRoomId,
        Long receiverId,
        String content
) {
}

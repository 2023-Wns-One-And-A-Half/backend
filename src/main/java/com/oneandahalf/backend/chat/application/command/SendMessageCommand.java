package com.oneandahalf.backend.chat.application.command;

public record SendMessageCommand(
        Long senderId,
        Long chatRoomId,
        Long receiverId,
        String content
) {
}

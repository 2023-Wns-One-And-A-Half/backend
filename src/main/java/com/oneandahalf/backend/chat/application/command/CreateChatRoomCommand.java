package com.oneandahalf.backend.chat.application.command;

public record CreateChatRoomCommand(
        Long chatRequesterId,
        Long productId,
        Long clientId
) {
}

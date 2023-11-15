package com.oneandahalf.backend.chat.application.command;

import lombok.Builder;

@Builder
public record CreateChatRoomCommand(
        Long chatRequesterId,
        Long productId,
        Long clientId
) {
}

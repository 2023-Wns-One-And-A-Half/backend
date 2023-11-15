package com.oneandahalf.backend.chat.presentation.request;


import com.oneandahalf.backend.chat.application.command.SendMessageCommand;

public record SendMessageRequest(
        Long receiverId,
        String content
) {

    public SendMessageCommand toCommand(Long chatRoomId, Long memberId) {
        return SendMessageCommand.builder()
                .senderId(memberId)
                .chatRoomId(chatRoomId)
                .receiverId(receiverId)
                .content(content)
                .build();
    }
}

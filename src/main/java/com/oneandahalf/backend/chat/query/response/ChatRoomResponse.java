package com.oneandahalf.backend.chat.query.response;


import com.oneandahalf.backend.chat.domain.ProductChatRoom;
import java.util.List;
import lombok.Builder;

@Builder
public record ChatRoomResponse(
        Long id,
        String name,
        List<ChatMessageResponse> chatMessages
) {

    public static ChatRoomResponse from(ProductChatRoom chatRoom, List<ChatMessageResponse> chatMessages) {
        return new ChatRoomResponse(
                chatRoom.getId(),
                chatRoom.getRoomName(),
                chatMessages
        );
    }
}

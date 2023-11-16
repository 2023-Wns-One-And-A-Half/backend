package com.oneandahalf.backend.chat.query.dao;

import com.oneandahalf.backend.chat.domain.ChatMessage;
import com.oneandahalf.backend.chat.query.dao.support.ChatMessageQuerySupport;
import com.oneandahalf.backend.chat.query.response.ChatMessageResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ChatMessageDao {

    private static final Long FIND_ALL_MESSAGE = -1L;
    private final ChatMessageQuerySupport chatMessageQuerySupport;


    public List<ChatMessageResponse> findAllByChatRoom(Long memberId, Long chatRoomId) {
        return findNewMessages(memberId, chatRoomId, FIND_ALL_MESSAGE);
    }

    public List<ChatMessageResponse> findNewMessages(Long memberId, Long chatRoomId, Long lastMessageId) {
        return chatMessageQuerySupport.findAllByChatRoomIdAndIdGreaterThan(chatRoomId, lastMessageId)
                .stream()
                .map(it -> processRead(it, memberId))
                .map(ChatMessageResponse::from)
                .toList();
    }

    private ChatMessage processRead(ChatMessage message, Long memberId) {
        message.read(memberId);
        return message;
    }
}

package com.oneandahalf.backend.chat.query.dao.support;

import com.oneandahalf.backend.chat.domain.ChatMessage;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ChatMessageQuerySupport extends JpaRepository<ChatMessage, Long> {

    @Query("SELECT m FROM ChatMessage m WHERE m.chatRoom.id = :chatRoomId AND m.id > :lastMessageId")
    List<ChatMessage> findAllByChatRoomIdAndIdGreaterThan(
            @Param("chatRoomId") Long chatRoomId,
            @Param("lastMessageId") Long lastMessageId
    );
}

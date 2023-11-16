package com.oneandahalf.backend.chat.query;

import static org.springframework.http.HttpStatus.FORBIDDEN;

import com.oneandahalf.backend.chat.domain.ChatRoomRepository;
import com.oneandahalf.backend.chat.domain.ProductChatRoom;
import com.oneandahalf.backend.chat.query.dao.ChatMessageDao;
import com.oneandahalf.backend.chat.query.response.ChatMessageResponse;
import com.oneandahalf.backend.chat.query.response.ChatRoomResponse;
import com.oneandahalf.backend.common.exception.ApplicationException;
import com.oneandahalf.backend.common.exception.ErrorCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ChatQueryService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageDao chatMessageDao;

    @Transactional
    public ChatRoomResponse findChatRoom(Long memberId, Long chatRoomId) {
        ProductChatRoom room = chatRoomRepository.getById(chatRoomId);
        if (!room.isParticipant(memberId)) {
            throw new ApplicationException(new ErrorCode(FORBIDDEN, "채팅을 조회할 권한이 없습니다"));
        }
        return ChatRoomResponse.from(room, chatMessageDao.findAllByChatRoom(memberId, chatRoomId));
    }

    public List<ChatMessageResponse> findNewMessages(Long memberId, Long chatRoomId, Long lastMessageId) {
        return chatMessageDao.findNewMessages(memberId, chatRoomId, lastMessageId);
    }
}

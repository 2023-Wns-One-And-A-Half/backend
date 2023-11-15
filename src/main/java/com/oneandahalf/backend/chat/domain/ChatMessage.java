package com.oneandahalf.backend.chat.domain;

import com.oneandahalf.backend.chat.exception.BadChatException;
import com.oneandahalf.backend.common.domain.CommonDomainModel;
import com.oneandahalf.backend.member.domain.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity

public class ChatMessage extends CommonDomainModel {

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id")
    private ProductChatRoom chatRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private Member sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recevier_id")
    private Member receiver;

    private boolean read;

    @Builder
    public ChatMessage(String content, ProductChatRoom chatRoom, Member sender, Member receiver) {
        this.content = content;
        this.chatRoom = chatRoom;
        this.sender = sender;
        this.receiver = receiver;
        validateMessage();
    }

    private void validateMessage() {
        if (sender.getId().equals(receiver.getId())) {
            throw new BadChatException("메세지 전송자와 수신자는 같을 수 없습니다.");
        }
        if (!chatRoom.isParticipant(receiver.getId())) {
            throw new BadChatException("메세지 수신자가 채팅에 참여된 사람이 아닙니다.");
        }
        if (!chatRoom.isParticipant(sender.getId())) {
            throw new BadChatException("메세지 발싡자가 채팅에 참여된 사람이 아닙니다.");
        }
    }

    public void read(Long memberId) {
        if (memberId.equals(receiver.getId())) {
            this.read = true;
        }
    }
}

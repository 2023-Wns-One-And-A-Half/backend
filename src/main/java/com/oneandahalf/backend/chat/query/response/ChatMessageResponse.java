package com.oneandahalf.backend.chat.query.response;

import com.oneandahalf.backend.chat.domain.ChatMessage;
import com.oneandahalf.backend.member.domain.ActivityArea;
import com.oneandahalf.backend.member.domain.Member;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record ChatMessageResponse(
        Long id,
        String content,
        MemberInfo senderInfo,
        MemberInfo receiverInfo,
        LocalDateTime createdDate,
        boolean read
) {

    public static ChatMessageResponse from(ChatMessage chatMessage) {
        return ChatMessageResponse.builder()
                .id(chatMessage.getId())
                .content(chatMessage.getContent())
                .senderInfo(MemberInfo.from(chatMessage.getSender()))
                .receiverInfo(MemberInfo.from(chatMessage.getReceiver()))
                .read(chatMessage.isRead())
                .createdDate(chatMessage.getCreatedDate())
                .build();
    }

    @Builder
    public record MemberInfo(
            Long id,
            String nickname,
            String profileImageName,
            ActivityArea activityArea
    ) {
        public static MemberInfo from(Member member) {
            return MemberInfo.builder()
                    .id(member.getId())
                    .nickname(member.getNickname())
                    .profileImageName(member.getProfileImageName())
                    .activityArea(member.getActivityArea())
                    .build();
        }
    }
}

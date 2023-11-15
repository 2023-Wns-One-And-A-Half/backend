package com.oneandahalf.backend.member.query.response;

import com.oneandahalf.backend.member.domain.ActivityArea;
import com.oneandahalf.backend.member.domain.Member;
import lombok.Builder;

@Builder
public record NotBlacklistMemberResponse(
        Long id,
        String nickname,
        String profileImageName,
        ActivityArea activityArea
) {

    public static NotBlacklistMemberResponse from(Member member) {
        return NotBlacklistMemberResponse.builder()
                .id(member.getId())
                .nickname(member.getNickname())
                .profileImageName(member.getProfileImageName())
                .activityArea(member.getActivityArea())
                .build();
    }
}

package com.oneandahalf.backend.member.query.response;

import com.oneandahalf.backend.member.domain.ActivityArea;
import com.oneandahalf.backend.member.domain.Member;
import lombok.Builder;

@Builder
public record MemberProfileResponse(
        Long id,
        String nickname,
        String profileImageName,
        ActivityArea activityArea,
        boolean black
) {

    public static MemberProfileResponse from(Member member) {
        return MemberProfileResponse.builder()
                .id(member.getId())
                .nickname(member.getNickname())
                .profileImageName(member.getProfileImageName())
                .activityArea(member.getActivityArea())
                .black(member.isBlack())
                .build();
    }
}

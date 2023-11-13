package com.oneandahalf.backend.member.application.command;

import com.oneandahalf.backend.member.domain.ActivityArea;
import com.oneandahalf.backend.member.domain.Member;
import lombok.Builder;

@Builder
public record SignupCommand(
        String username,
        String password,
        ActivityArea activityArea,
        String nickname,
        String profileImageName
) {

    public Member toDomain() {
        return Member.builder()
                .username(username)
                .password(password)
                .activityArea(activityArea)
                .nickname(nickname)
                .profileImageName(profileImageName)
                .build();
    }
}

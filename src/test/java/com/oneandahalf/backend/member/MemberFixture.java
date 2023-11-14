package com.oneandahalf.backend.member;

import static com.oneandahalf.backend.member.domain.ActivityArea.SEOUL;

import com.oneandahalf.backend.member.domain.Member;

public class MemberFixture {

    public static String MALLANG_PASSWORD = "mallang1234!@#$";

    public static Member mallang() {
        return Member.builder()
                .username("mallang1234")
                .password(MALLANG_PASSWORD)
                .activityArea(SEOUL)
                .nickname("mallang")
                .profileImageName("mallang_profile")
                .build();
    }

    public static Member member(String nickname) {
        return Member.builder()
                .username(nickname + "12345678")
                .password(nickname + "1234!@#$")
                .activityArea(SEOUL)
                .nickname(nickname)
                .profileImageName(nickname + "_profile")
                .build();
    }
}

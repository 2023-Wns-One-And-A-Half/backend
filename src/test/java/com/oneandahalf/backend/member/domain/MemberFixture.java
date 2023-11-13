package com.oneandahalf.backend.member.domain;

public class MemberFixture {

    public static Member mallang() {
        return Member.builder()
                .username("mallang1234")
                .password("mallang1234!@#$")
                .activityArea(ActivityArea.SEOUL)
                .nickname("mallang")
                .profileImageName("mallang_profile")
                .build();
    }
}

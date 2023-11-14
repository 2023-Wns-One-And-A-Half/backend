package com.oneandahalf.backend.member;

import com.oneandahalf.backend.member.domain.ActivityArea;
import com.oneandahalf.backend.member.domain.Member;

public class MemberFixture {

    public static String MALLANG_PASSWORD = "mallang1234!@#$";

    public static Member mallang() {
        return Member.builder()
                .username("mallang1234")
                .password(MALLANG_PASSWORD)
                .activityArea(ActivityArea.SEOUL)
                .nickname("mallang")
                .profileImageName("mallang_profile")
                .build();
    }
}

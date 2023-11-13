package com.oneandahalf.backend.member.domain;

import static com.oneandahalf.backend.member.domain.MemberFixture.MALLANG_PASSWORD;
import static com.oneandahalf.backend.member.domain.MemberFixture.mallang;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.mock;

import com.oneandahalf.backend.member.exeception.DuplicateUsernameException;
import com.oneandahalf.backend.member.exeception.MissMatchPasswordException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;

@DisplayName("회원(Member) 은(는)")
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
class MemberTest {

    private final MemberValidator memberValidator = mock(MemberValidator.class);

    @Test
    void 회원가입_시_아이디_중복검사를_수행한다() {
        // given
        Member mallang = mallang();
        willThrow(DuplicateUsernameException.class)
                .given(memberValidator)
                .validateDuplicateUsername(mallang.getUsername());

        // when & then
        assertThatThrownBy(() -> {
            mallang.signup(memberValidator);
        }).isInstanceOf(DuplicateUsernameException.class);
    }

    @Test
    void 비밀번호가_일치하면_로그인_성공() {
        // given
        Member mallang = mallang();

        // when & then
        assertDoesNotThrow(() -> {
            mallang.login(MALLANG_PASSWORD);
        });
    }

    @Test
    void 비밀번호가_일차하지_않으면_로그인_실패() {
        // given
        Member mallang = mallang();

        // when & then
        assertThatThrownBy(() -> {
            mallang.login(MALLANG_PASSWORD + "wrong");
        }).isInstanceOf(MissMatchPasswordException.class);
    }
}

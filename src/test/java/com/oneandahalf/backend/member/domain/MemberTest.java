package com.oneandahalf.backend.member.domain;

import static com.oneandahalf.backend.member.domain.MemberFixture.mallang;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.mock;

import com.oneandahalf.backend.member.exeception.DuplicateUsernameException;
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
}

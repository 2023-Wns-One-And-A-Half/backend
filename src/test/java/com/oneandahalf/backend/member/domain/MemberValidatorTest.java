package com.oneandahalf.backend.member.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import com.oneandahalf.backend.member.exeception.DuplicateUsernameException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;

@DisplayName("회원 검증기(MemberValidator) 은(는)")
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
class MemberValidatorTest {

    private final MemberRepository memberRepository = mock(MemberRepository.class);
    private final MemberValidator memberValidator = new MemberValidator(memberRepository);

    @Test
    void 중복된_아이디로_가입한_회원이_이미_있으면_오류이다() {
        // given
        Username username = new Username("mallang1234");
        given(memberRepository.existsByUsername(username))
                .willReturn(true);

        // when & then
        assertThatThrownBy(() -> {
            memberValidator.validateDuplicateUsername(username);
        }).isInstanceOf(DuplicateUsernameException.class);
    }

    @Test
    void 중복된_아이디로_가입한_회원이_없으면_통과() {
        // given
        Username username = new Username("mallang1234");
        given(memberRepository.existsByUsername(username))
                .willReturn(false);

        // when & then
        assertDoesNotThrow(() -> {
            memberValidator.validateDuplicateUsername(username);
        });
    }
}


package com.oneandahalf.backend.member.domain;

import static com.oneandahalf.backend.member.MemberFixture.mallang;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import com.oneandahalf.backend.member.domain.blacklist.Blacklist;
import com.oneandahalf.backend.member.domain.blacklist.BlacklistRepository;
import com.oneandahalf.backend.member.exception.DuplicateUsernameException;
import com.oneandahalf.backend.member.exception.ForbiddenBlacklistException;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;

@DisplayName("회원 검증기(MemberValidator) 은(는)")
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
class MemberValidatorTest {

    private final BlacklistRepository blacklistRepository = mock(BlacklistRepository.class);
    private final MemberRepository memberRepository = mock(MemberRepository.class);
    private final MemberValidator memberValidator = new MemberValidator(blacklistRepository, memberRepository);

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

    @Test
    void 로그인_시_블랙리스트면_오류() {
        // given
        given(blacklistRepository.findByMemberId(any()))
                .willReturn(Optional.of(new Blacklist(mallang())));

        // when & then
        assertThatThrownBy(() -> {
            memberValidator.validateBlackListLogin(1L);
        }).isInstanceOf(ForbiddenBlacklistException.class);
    }
}


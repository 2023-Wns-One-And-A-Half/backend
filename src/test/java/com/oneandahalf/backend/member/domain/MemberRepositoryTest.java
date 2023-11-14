package com.oneandahalf.backend.member.domain;

import static com.oneandahalf.backend.member.MemberFixture.mallang;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DisplayName("회원 Repository(MemberRepository) 은(는)")
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void 아이디로_회원이_존재하는지_찾는다() {
        // given
        Member member = mallang();
        memberRepository.save(member);

        // when & then
        assertThat(memberRepository.existsByUsername(
                new Username("mallang1234")
        )).isTrue();
        assertThat(memberRepository.existsByUsername(
                new Username("mallang123")
        )).isFalse();
    }
}

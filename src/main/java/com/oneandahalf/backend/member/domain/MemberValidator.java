package com.oneandahalf.backend.member.domain;

import com.oneandahalf.backend.member.domain.blacklist.BlacklistRepository;
import com.oneandahalf.backend.member.exception.DuplicateUsernameException;
import com.oneandahalf.backend.member.exception.ForbiddenBlacklistException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MemberValidator {

    private final BlacklistRepository blacklistRepository;
    private final MemberRepository memberRepository;

    public void validateDuplicateUsername(Username username) {
        if (memberRepository.existsByUsername(username)) {
            throw new DuplicateUsernameException();
        }
    }

    public void validateBlackListLogin(Long memberId) {
        if (blacklistRepository.findByMemberId(memberId).isPresent()) {
            throw new ForbiddenBlacklistException();
        }
    }
}

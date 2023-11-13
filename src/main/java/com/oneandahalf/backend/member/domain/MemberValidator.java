package com.oneandahalf.backend.member.domain;

import com.oneandahalf.backend.member.exeception.DuplicateUsernameException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MemberValidator {

    private final MemberRepository memberRepository;

    public void validateDuplicateUsername(Username username) {
        if (memberRepository.existsByUsername(username)) {
            throw new DuplicateUsernameException();
        }
    }
}

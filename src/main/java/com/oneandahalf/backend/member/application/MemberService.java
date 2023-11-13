package com.oneandahalf.backend.member.application;

import com.oneandahalf.backend.member.application.command.LoginCommand;
import com.oneandahalf.backend.member.application.command.SignupCommand;
import com.oneandahalf.backend.member.domain.Member;
import com.oneandahalf.backend.member.domain.MemberRepository;
import com.oneandahalf.backend.member.domain.MemberValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberValidator memberValidator;

    public Long signup(SignupCommand command) {
        Member member = command.toDomain();
        member.signup(memberValidator);
        return memberRepository.save(member)
                .getId();
    }

    public Long login(LoginCommand command) {
        Member member = memberRepository.getByUsername(command.username());
        member.login(command.password());
        return member.getId();
    }
}

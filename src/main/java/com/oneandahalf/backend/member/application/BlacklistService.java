package com.oneandahalf.backend.member.application;

import com.oneandahalf.backend.member.domain.Member;
import com.oneandahalf.backend.member.domain.MemberRepository;
import com.oneandahalf.backend.member.domain.blacklist.Blacklist;
import com.oneandahalf.backend.member.domain.blacklist.BlacklistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class BlacklistService {

    private final BlacklistRepository blacklistRepository;
    private final MemberRepository memberRepository;

    public void black(Long memberId) {
        Member member = memberRepository.getById(memberId);
        blacklistRepository.save(new Blacklist(member));
    }

    public void unBlack(Long memberId) {
        Blacklist blackList = blacklistRepository.getByMemberId(memberId);
        blackList.delete();
        blacklistRepository.delete(blackList);
    }
}

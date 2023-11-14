package com.oneandahalf.backend.keyword.application;

import com.oneandahalf.backend.keyword.application.command.CreateKeywordCommand;
import com.oneandahalf.backend.keyword.application.command.DeleteKeywordCommand;
import com.oneandahalf.backend.keyword.domain.Keyword;
import com.oneandahalf.backend.keyword.domain.KeywordRepository;
import com.oneandahalf.backend.keyword.domain.KeywordValidator;
import com.oneandahalf.backend.member.domain.Member;
import com.oneandahalf.backend.member.domain.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class KeywordService {

    private final MemberRepository memberRepository;
    private final KeywordRepository keywordRepository;
    private final KeywordValidator keywordValidator;

    public Long create(CreateKeywordCommand command) {
        Member member = memberRepository.getById(command.memberId());
        Keyword keyword = command.toDomain(member);
        keyword.create(keywordValidator);
        return keywordRepository.save(keyword)
                .getId();
    }

    public void delete(DeleteKeywordCommand command) {
        keywordRepository.deleteByIdAndMemberId(command.keywordId(), command.memberId());
    }
}

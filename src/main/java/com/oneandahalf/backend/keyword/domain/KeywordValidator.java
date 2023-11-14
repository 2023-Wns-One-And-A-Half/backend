package com.oneandahalf.backend.keyword.domain;

import com.oneandahalf.backend.keyword.exception.DuplicateKeywordException;
import com.oneandahalf.backend.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class KeywordValidator {

    private final KeywordRepository keywordRepository;

    public void validateDuplicateKeyword(Member member, String content) {
        if (keywordRepository.existsByMemberAndContent(member, content)) {
            throw new DuplicateKeywordException();
        }
    }
}

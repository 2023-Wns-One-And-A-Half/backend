package com.oneandahalf.backend.keyword.application.command;

import com.oneandahalf.backend.keyword.domain.Keyword;
import com.oneandahalf.backend.member.domain.Member;

public record CreateKeywordCommand(
        Long memberId,
        String content
) {

    public Keyword toDomain(Member member) {
        return new Keyword(content, member);
    }
}

package com.oneandahalf.backend.keyword.application.command;

import lombok.Builder;

@Builder
public record DeleteKeywordCommand(
        Long keywordId,
        Long memberId
) {
}

package com.oneandahalf.backend.keyword.application.command;

public record DeleteKeywordCommand(
        Long keywordId,
        Long memberId
) {
}

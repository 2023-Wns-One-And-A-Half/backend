package com.oneandahalf.backend.keyword.presentation.request;

import com.oneandahalf.backend.keyword.application.command.CreateKeywordCommand;

public record CreateKeywordRequest(
        String content
) {

    public CreateKeywordCommand toCommand(Long memberId) {
        return new CreateKeywordCommand(memberId, content);
    }
}

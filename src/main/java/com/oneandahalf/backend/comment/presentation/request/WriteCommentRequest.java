package com.oneandahalf.backend.comment.presentation.request;

import com.oneandahalf.backend.comment.application.command.WriteCommentCommand;

public record WriteCommentRequest(
        Long productId,
        String content
) {

    public WriteCommentCommand toCommand(Long memberId) {
        return WriteCommentCommand.builder()
                .memberId(memberId)
                .productId(productId)
                .content(content)
                .build();
    }
}

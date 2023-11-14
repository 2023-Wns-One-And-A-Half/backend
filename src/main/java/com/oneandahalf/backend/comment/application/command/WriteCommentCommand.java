package com.oneandahalf.backend.comment.application.command;

import com.oneandahalf.backend.comment.domain.Comment;
import com.oneandahalf.backend.member.domain.Member;
import com.oneandahalf.backend.product.domain.Product;
import lombok.Builder;

@Builder
public record WriteCommentCommand(
        Long memberId,
        Long productId,
        String content
) {

    public Comment toDomain(Member writer, Product product) {
        return new Comment(content, writer, product);
    }
}

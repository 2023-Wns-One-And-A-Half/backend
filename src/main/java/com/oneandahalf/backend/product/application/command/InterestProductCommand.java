package com.oneandahalf.backend.product.application.command;

public record InterestProductCommand(
        Long memberId,
        Long productId
) {
}

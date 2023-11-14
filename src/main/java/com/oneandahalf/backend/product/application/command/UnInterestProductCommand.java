package com.oneandahalf.backend.product.application.command;

public record UnInterestProductCommand(
        Long memberId,
        Long productId
) {
}

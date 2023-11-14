package com.oneandahalf.backend.product.presentation.request;

import com.oneandahalf.backend.product.application.command.InterestProductCommand;

public record InterestProductRequest(
        Long productId
) {
    public InterestProductCommand toCommand(Long memberId) {
        return new InterestProductCommand(memberId, productId);
    }
}

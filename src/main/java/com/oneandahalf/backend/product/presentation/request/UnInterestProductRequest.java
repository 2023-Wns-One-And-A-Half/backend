package com.oneandahalf.backend.product.presentation.request;

import com.oneandahalf.backend.product.application.command.UnInterestProductCommand;

public record UnInterestProductRequest(
        Long productId
) {

    public UnInterestProductCommand toCommand(Long memberId) {
        return new UnInterestProductCommand(memberId, productId);
    }
}

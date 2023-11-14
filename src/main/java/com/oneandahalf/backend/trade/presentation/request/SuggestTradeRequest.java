package com.oneandahalf.backend.trade.presentation.request;

import com.oneandahalf.backend.trade.application.command.SuggestTradeCommand;

public record SuggestTradeRequest(
        Long productId
) {

    public SuggestTradeCommand toCommand(Long memberId) {
        return new SuggestTradeCommand(memberId, productId);
    }
}

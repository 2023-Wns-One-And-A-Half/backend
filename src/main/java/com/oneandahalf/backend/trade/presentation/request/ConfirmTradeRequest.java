package com.oneandahalf.backend.trade.presentation.request;

import com.oneandahalf.backend.trade.application.command.ConfirmTradeCommand;

public record ConfirmTradeRequest(
        Long tradeSuggestionId
) {

    public ConfirmTradeCommand toCommand(Long sellerId) {
        return new ConfirmTradeCommand(sellerId, tradeSuggestionId);
    }
}

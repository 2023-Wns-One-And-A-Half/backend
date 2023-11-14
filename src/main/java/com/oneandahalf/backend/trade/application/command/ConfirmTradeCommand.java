package com.oneandahalf.backend.trade.application.command;

public record ConfirmTradeCommand(
        Long sellerId,
        Long tradeSuggestionId
) {
}

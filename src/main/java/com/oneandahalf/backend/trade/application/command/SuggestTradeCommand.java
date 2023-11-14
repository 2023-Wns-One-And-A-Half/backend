package com.oneandahalf.backend.trade.application.command;

public record SuggestTradeCommand(
        Long suggesterId,
        Long productId
) {
}

package com.oneandahalf.backend.trade.query.response;

import com.oneandahalf.backend.trade.domain.TradeSuggestion;
import java.time.LocalDateTime;

public record TradeSuggestionStatusResponse(
        boolean suggested,
        LocalDateTime suggestedDate
) {
    public static TradeSuggestionStatusResponse from(TradeSuggestion suggestion) {
        return new TradeSuggestionStatusResponse(true, suggestion.getCreatedDate());
    }
}

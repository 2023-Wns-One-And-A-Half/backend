package com.oneandahalf.backend.trade.query.response;

import com.oneandahalf.backend.trade.domain.TradeSuggestion;
import java.time.LocalDateTime;

public record TradeSuggestionExistResponse(
        boolean suggested,
        LocalDateTime suggestedDate
) {
    public static TradeSuggestionExistResponse from(TradeSuggestion suggestion) {
        return new TradeSuggestionExistResponse(true, suggestion.getCreatedDate());
    }
}

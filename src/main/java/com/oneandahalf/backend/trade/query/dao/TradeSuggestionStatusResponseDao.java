package com.oneandahalf.backend.trade.query.dao;

import com.oneandahalf.backend.trade.query.dao.support.TradeSuggestionQuerySupport;
import com.oneandahalf.backend.trade.query.response.TradeSuggestionStatusResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class TradeSuggestionStatusResponseDao {

    private final TradeSuggestionQuerySupport tradeSuggestionQuerySupport;

    public TradeSuggestionStatusResponse find(Long suggesterId, Long productId) {
        return tradeSuggestionQuerySupport.findBySuggesterIdAndProductId(suggesterId, productId)
                .map(TradeSuggestionStatusResponse::from)
                .orElseGet(() -> new TradeSuggestionStatusResponse(false, null));
    }
}

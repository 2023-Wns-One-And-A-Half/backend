package com.oneandahalf.backend.trade.query.dao;

import com.oneandahalf.backend.trade.query.dao.support.TradeSuggestionQuerySupport;
import com.oneandahalf.backend.trade.query.response.TradeSuggestionResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class TradeSuggestionResponseDao {

    private final TradeSuggestionQuerySupport tradeSuggestionQuerySupport;

    public List<TradeSuggestionResponse> findAll(Long productId) {
        return tradeSuggestionQuerySupport.findAllWithSuggesterByProductId(productId)
                .stream()
                .map(TradeSuggestionResponse::from)
                .toList();
    }
}

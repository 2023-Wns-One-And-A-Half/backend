package com.oneandahalf.backend.trade.query;

import com.oneandahalf.backend.trade.query.dao.TradeSuggestionStatusResponseDao;
import com.oneandahalf.backend.trade.query.response.TradeSuggestionStatusResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class TradeSuggestionQueryService {

    private final TradeSuggestionStatusResponseDao tradeSuggestionStatusResponseDao;

    public TradeSuggestionStatusResponse findStatus(Long suggesterId, Long productId) {
        return tradeSuggestionStatusResponseDao.find(suggesterId, productId);
    }
}

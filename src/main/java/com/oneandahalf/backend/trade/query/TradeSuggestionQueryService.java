package com.oneandahalf.backend.trade.query;

import com.oneandahalf.backend.trade.query.dao.TradeSuggestionResponseDao;
import com.oneandahalf.backend.trade.query.dao.TradeSuggestionStatusResponseDao;
import com.oneandahalf.backend.trade.query.response.TradeSuggestionResponse;
import com.oneandahalf.backend.trade.query.response.TradeSuggestionStatusResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class TradeSuggestionQueryService {

    private final TradeSuggestionStatusResponseDao tradeSuggestionStatusResponseDao;
    private final TradeSuggestionResponseDao tradeSuggestionResponseDao;

    public TradeSuggestionStatusResponse findStatus(Long suggesterId, Long productId) {
        return tradeSuggestionStatusResponseDao.find(suggesterId, productId);
    }

    public List<TradeSuggestionResponse> findAllByProductId(Long productId) {
        return tradeSuggestionResponseDao.findAll(productId);
    }
}

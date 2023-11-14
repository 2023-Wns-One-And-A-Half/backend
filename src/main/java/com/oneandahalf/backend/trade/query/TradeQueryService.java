package com.oneandahalf.backend.trade.query;

import com.oneandahalf.backend.trade.query.dao.MyTradeProductResponseDao;
import com.oneandahalf.backend.trade.query.response.MyTradeProductResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class TradeQueryService {

    private final MyTradeProductResponseDao myTradeProductResponseDao;

    public List<MyTradeProductResponse> findPurchased(Long memberId) {
        return myTradeProductResponseDao.findPurchased(memberId);
    }

    public List<MyTradeProductResponse> findSold(Long memberId) {
        return myTradeProductResponseDao.findSold(memberId);
    }
}

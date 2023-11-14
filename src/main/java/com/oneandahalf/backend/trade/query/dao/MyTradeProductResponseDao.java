package com.oneandahalf.backend.trade.query.dao;

import static com.oneandahalf.backend.product.domain.QProduct.product;
import static com.oneandahalf.backend.trade.domain.QTrade.trade;

import com.oneandahalf.backend.trade.query.response.MyTradeProductResponse;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MyTradeProductResponseDao {

    private final JPAQueryFactory query;

    public List<MyTradeProductResponse> findPurchased(Long memberId) {
        return query.selectFrom(product)
                .join(trade)
                .on(trade.product.eq(product))
                .where(trade.purchaser.id.eq(memberId))
                .orderBy(trade.id.desc())
                .fetch()
                .stream()
                .map(MyTradeProductResponse::from)
                .toList();
    }

    public List<MyTradeProductResponse> findSold(Long memberId) {
        return query.selectFrom(product)
                .join(trade)
                .on(trade.product.eq(product))
                .where(trade.product.seller.id.eq(memberId))
                .orderBy(trade.id.desc())
                .fetch()
                .stream()
                .map(MyTradeProductResponse::from)
                .toList();
    }
}

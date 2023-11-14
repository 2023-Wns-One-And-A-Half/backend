package com.oneandahalf.backend.trade.domain;

import com.oneandahalf.backend.member.domain.Member;
import com.oneandahalf.backend.product.domain.Product;
import com.oneandahalf.backend.trade.exception.AlreadyTradedProductException;
import com.oneandahalf.backend.trade.exception.DuplicateTradeSuggestionException;
import com.oneandahalf.backend.trade.exception.NoAuthoritySellProductException;
import com.oneandahalf.backend.trade.exception.SelfPurchaseForbiddenException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class TradeValidator {

    private final TradeSuggestionRepository tradeSuggestionRepository;
    private final TradeRepository tradeRepository;

    public void validateSuggest(Member suggester, Product product) {
        validateAlreadyTraded(product);
        validateSuggestionAlreadyExist(suggester, product);
        validateSelfPurchase(suggester, product);
    }

    public void validateTradeConfirm(Product product, Member seller) {
        validateProductSeller(product, seller);
        validateAlreadyTraded(product);
    }

    private void validateAlreadyTraded(Product product) {
        if (tradeRepository.existsByProduct(product)) {
            throw new AlreadyTradedProductException();
        }
    }

    private void validateSuggestionAlreadyExist(Member suggester, Product product) {
        if (tradeSuggestionRepository.existsBySuggesterAndProduct(suggester, product)) {
            throw new DuplicateTradeSuggestionException();
        }
    }

    private void validateSelfPurchase(Member suggester, Product product) {
        if (product.getSeller().equals(suggester)) {
            throw new SelfPurchaseForbiddenException();
        }
    }

    private void validateProductSeller(Product product, Member seller) {
        if (!product.getSeller().equals(seller)) {
            throw new NoAuthoritySellProductException();
        }
    }
}

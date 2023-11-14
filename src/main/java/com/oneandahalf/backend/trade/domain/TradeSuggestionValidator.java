package com.oneandahalf.backend.trade.domain;

import com.oneandahalf.backend.member.domain.Member;
import com.oneandahalf.backend.product.domain.Product;
import com.oneandahalf.backend.trade.exception.DuplicateTradeSuggestionException;
import com.oneandahalf.backend.trade.exception.SelfPurchaseForbiddenException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class TradeSuggestionValidator {

    private final TradeSuggestionRepository tradeSuggestionRepository;

    public void validateSuggest(Member suggester, Product product) {
        // TODO 상품이 이미 거래되지 않았는지
        validateSuggestionAlreadyExist(suggester, product);
        validateSelfPurchase(suggester, product);
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
}

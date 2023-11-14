package com.oneandahalf.backend.trade.domain;

import com.oneandahalf.backend.common.domain.CommonDomainModel;
import com.oneandahalf.backend.member.domain.Member;
import com.oneandahalf.backend.product.domain.Product;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class TradeSuggestion extends CommonDomainModel {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "suggester_id")
    private Member suggester;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    public TradeSuggestion(Member suggester, Product product) {
        this.suggester = suggester;
        this.product = product;
    }

    public void suggest(TradeSuggestionValidator tradeSuggestionValidator) {
        tradeSuggestionValidator.validateSuggest(suggester, product);
    }
}

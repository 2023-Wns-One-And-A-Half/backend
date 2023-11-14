package com.oneandahalf.backend.trade.domain;

import static com.oneandahalf.backend.member.MemberFixture.member;
import static com.oneandahalf.backend.product.ProductFixture.product;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import com.oneandahalf.backend.member.domain.Member;
import com.oneandahalf.backend.product.domain.Product;
import com.oneandahalf.backend.trade.exception.DuplicateTradeSuggestionException;
import com.oneandahalf.backend.trade.exception.SelfPurchaseForbiddenException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;

@DisplayName("거래 제안 검증기(TradeSuggestionValidator) 은(는)")
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
class TradeSuggestionValidatorTest {

    private final TradeSuggestionRepository tradeSuggestionRepository = mock(TradeSuggestionRepository.class);
    private final TradeSuggestionValidator tradeSuggestionValidator = new TradeSuggestionValidator(tradeSuggestionRepository);

    @Test
    void 이미_거래_요청을_한_상품에_또_한_경우_예외() {
        // given
        Member suggester = member("구매요청자");
        Member seller = member("판매자");
        Product product = product(seller);
        given(tradeSuggestionRepository.existsBySuggesterAndProduct(suggester, product))
                .willReturn(true);

        // when & then
        assertThatThrownBy(() -> {
            tradeSuggestionValidator.validateSuggest(suggester, product);
        }).isInstanceOf(DuplicateTradeSuggestionException.class);
    }

    @Test
    void 자신의_상품을_구매하려는_경우_예외() {
        // given
        Member suggester = member("구매요청자");
        Product product = product(suggester);
        given(tradeSuggestionRepository.existsBySuggesterAndProduct(suggester, product))
                .willReturn(false);

        // when & then
        assertThatThrownBy(() -> {
            tradeSuggestionValidator.validateSuggest(suggester, product);
        }).isInstanceOf(SelfPurchaseForbiddenException.class);
    }
}

package com.oneandahalf.backend.trade.domain;

import static com.oneandahalf.backend.member.MemberFixture.member;
import static com.oneandahalf.backend.product.ProductFixture.product;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import com.oneandahalf.backend.member.domain.Member;
import com.oneandahalf.backend.product.domain.Product;
import com.oneandahalf.backend.trade.exception.AlreadyTradedProductException;
import com.oneandahalf.backend.trade.exception.DuplicateTradeSuggestionException;
import com.oneandahalf.backend.trade.exception.NoAuthoritySellProductException;
import com.oneandahalf.backend.trade.exception.SelfPurchaseForbiddenException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("거래 제안 검증기(TradeValidator) 은(는)")
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
class TradeValidatorTest {

    private final TradeSuggestionRepository tradeSuggestionRepository = mock(TradeSuggestionRepository.class);
    private final TradeRepository tradeRepository = mock(TradeRepository.class);
    private final TradeValidator tradeValidator = new TradeValidator(tradeSuggestionRepository, tradeRepository);

    @Nested
    class 거래_제안_검증_시 {

        @Test
        void 이미_판매된_상품이라면_예외() {
            // given
            Member suggester = member("구매요청자");
            Member seller = member("판매자");
            Product product = product(seller);
            given(tradeRepository.existsByProduct(product))
                    .willReturn(true);

            // when & then
            assertThatThrownBy(() -> {
                tradeValidator.validateSuggest(suggester, product);
            }).isInstanceOf(AlreadyTradedProductException.class);
        }

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
                tradeValidator.validateSuggest(suggester, product);
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
                tradeValidator.validateSuggest(suggester, product);
            }).isInstanceOf(SelfPurchaseForbiddenException.class);
        }
    }

    @Nested
    class 거래_확정_검증_시 {

        @Test
        void 이미_판매된_상품이라면_예외() {
            // given
            Member suggester = member("구매요청자");
            Member seller = member("판매자");
            Product product = product(seller);
            given(tradeRepository.existsByProduct(product))
                    .willReturn(true);

            // when & then
            assertThatThrownBy(() -> {
                tradeValidator.validateTradeConfirm(product, seller);
            }).isInstanceOf(AlreadyTradedProductException.class);
        }

        @Test
        void 거래_확정을_시도한_사람이_상품의_판매자가_아닌_경우() {
            // given
            Member suggester = member("구매요청자");
            Member seller = member("판매자");
            Product product = product(seller);
            given(tradeRepository.existsByProduct(product))
                    .willReturn(false);

            // when & then
            assertThatThrownBy(() -> {
                tradeValidator.validateTradeConfirm(product, suggester);
            }).isInstanceOf(NoAuthoritySellProductException.class);
        }
    }
}

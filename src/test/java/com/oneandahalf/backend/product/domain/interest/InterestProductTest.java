package com.oneandahalf.backend.product.domain.interest;

import static com.oneandahalf.backend.member.MemberFixture.mallang;
import static com.oneandahalf.backend.product.ProductFixture.product;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.mock;

import com.oneandahalf.backend.member.domain.Member;
import com.oneandahalf.backend.product.domain.Product;
import com.oneandahalf.backend.product.exception.DuplicateInterestException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;

@DisplayName("관심 상품(InterestProduct) 은(는)")
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
class InterestProductTest {

    private final InterestProductValidator validator = mock(InterestProductValidator.class);

    @Test
    void 중복_등록이라면_예외() {
        // given
        Member mallang = mallang();
        Product product = product(mallang);
        willThrow(DuplicateInterestException.class)
                .given(validator)
                .validateDuplicate(mallang, product);

        // when & then
        assertThatThrownBy(() -> {
            validator.validateDuplicate(mallang, product);
        }).isInstanceOf(DuplicateInterestException.class);
    }

    @Test
    void 중복등록이_아니라면_등록() {
        // given
        Member mallang = mallang();
        Product product = product(mallang);
        willDoNothing()
                .given(validator)
                .validateDuplicate(mallang, product);

        // when & then
        assertDoesNotThrow(() -> {
            validator.validateDuplicate(mallang, product);
        });
    }
}

package com.oneandahalf.backend.product.domain.interest;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import com.oneandahalf.backend.member.domain.Member;
import com.oneandahalf.backend.product.domain.Product;
import com.oneandahalf.backend.product.exception.DuplicateInterestException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;

@DisplayName("관심 상품 검증기(InterestProductValidator) 은(는)")
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
class InterestProductValidatorTest {

    private final InterestProductRepository repository = mock(InterestProductRepository.class);
    private final InterestProductValidator validator = new InterestProductValidator(repository);

    @Test
    void 중복_시_예외() {
        // given
        Member member = mock(Member.class);
        Product product = mock(Product.class);
        given(repository.existsByMemberAndProduct(member, product))
                .willReturn(true);

        // when & then
        assertThatThrownBy(() -> {
            validator.validateDuplicate(member, product);
        }).isInstanceOf(DuplicateInterestException.class);
    }

    @Test
    void 중복되지_않았다면_통과() {
        // given
        Member member = mock(Member.class);
        Product product = mock(Product.class);
        given(repository.existsByMemberAndProduct(member, product))
                .willReturn(false);

        // when & then
        assertDoesNotThrow(() -> {
            validator.validateDuplicate(member, product);
        });
    }
}

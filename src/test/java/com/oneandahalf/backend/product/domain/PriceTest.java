package com.oneandahalf.backend.product.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.oneandahalf.backend.product.exception.NegativePriceException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;

@DisplayName("가격(Price) 은(는)")
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
class PriceTest {

    @Test
    void 가격이_0원_미만이면_예외() {
        // when & then
        assertThatThrownBy(() ->
                new Price(-1)
        ).isInstanceOf(NegativePriceException.class);
    }
}

package com.oneandahalf.backend.product.domain;

import static lombok.AccessLevel.PROTECTED;

import com.oneandahalf.backend.product.exception.NegativePriceException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.Objects;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Embeddable
public class Price {

    @Column(name = "price")
    private int value;

    public Price(int value) {
        validateNotNegative(value);
        this.value = value;
    }

    private void validateNotNegative(int value) {
        if (value < 0) {
            throw new NegativePriceException();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Price price)) {
            return false;
        }
        return getValue() == price.getValue();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }
}

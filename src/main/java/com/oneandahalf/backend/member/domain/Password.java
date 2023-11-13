package com.oneandahalf.backend.member.domain;

import static lombok.AccessLevel.PROTECTED;

import com.oneandahalf.backend.member.exception.BadPasswordException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.Objects;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PROTECTED)
@Getter
@Embeddable
public class Password {

    @Column(name = "password", nullable = false)
    private String value;

    public Password(String value) {
        validateLength(value);
        this.value = value.strip();
    }

    private void validateLength(String value) {
        if (value == null || value.strip().length() < 8) {
            throw new BadPasswordException();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Password password)) {
            return false;
        }
        return Objects.equals(getValue(), password.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }

    public boolean match(String value) {
        return this.value.equals(value);
    }
}

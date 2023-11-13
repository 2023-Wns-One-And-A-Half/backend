package com.oneandahalf.backend.member.domain;

import static lombok.AccessLevel.PROTECTED;

import com.oneandahalf.backend.member.exception.BadUsernameException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.Objects;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PROTECTED)
@Getter
@Embeddable
public class Username {

    @Column(name = "username", nullable = false, unique = true)
    private String value;

    public Username(String value) {
        validateLength(value);
        this.value = value.strip();
    }

    private void validateLength(String value) {
        if (value == null || value.strip().length() < 8) {
            throw new BadUsernameException();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Username username)) {
            return false;
        }
        return Objects.equals(getValue(), username.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }
}


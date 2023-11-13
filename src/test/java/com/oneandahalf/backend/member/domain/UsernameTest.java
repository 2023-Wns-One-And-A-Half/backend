package com.oneandahalf.backend.member.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.oneandahalf.backend.member.exeception.BadUsernameException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("아이디(Username) 은(는)")
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
class UsernameTest {

    @ParameterizedTest
    @NullAndEmptySource
    void null_이나_빈값이면_예외(String value) {
        // when  & then
        assertThatThrownBy(() -> {
            new Username(value);
        }).isInstanceOf(BadUsernameException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "1",
            "12",
            "123",
            "1234",
            "12345",
            "123456",
            "1234567",
    })
    void 길이가_8글자_이하면_예외(String value) {
        // when  & then
        assertThatThrownBy(() -> {
            new Username(value);
        }).isInstanceOf(BadUsernameException.class);
    }

    @Test
    void 길이가_8글자_이상이어야_한다() {
        // when & then
        assertDoesNotThrow(() -> {
            new Username("12345678");
        });
    }
}

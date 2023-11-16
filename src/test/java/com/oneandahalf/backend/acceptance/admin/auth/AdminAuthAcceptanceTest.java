package com.oneandahalf.backend.acceptance.admin.auth;


import static com.oneandahalf.backend.acceptance.AcceptanceSteps.예외_메세지를_검증한다;
import static com.oneandahalf.backend.acceptance.AcceptanceSteps.응답_상태를_검증한다;
import static com.oneandahalf.backend.acceptance.AcceptanceSteps.인증되지_않음;
import static com.oneandahalf.backend.acceptance.AcceptanceSteps.찾을수_없음;
import static com.oneandahalf.backend.acceptance.admin.auth.AdminAuthAcceptanceSteps.어드민_로그인_요청;
import static org.assertj.core.api.Assertions.assertThat;

import com.oneandahalf.backend.acceptance.AcceptanceTest;
import com.oneandahalf.backend.admin.auth.presentation.request.AdminLoginRequest;
import io.restassured.http.Cookie;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("어드민 인증 인수테스트")
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
public class AdminAuthAcceptanceTest {

    @Nested
    class 로그인_API extends AcceptanceTest {

        @Test
        void 로그인에_성공하면_세션을_발급한다() {
            // given
            AdminLoginRequest request = new AdminLoginRequest("admin", "admin");

            // when
            var 응답 = 어드민_로그인_요청(request);

            // then
            Cookie cookie = 응답.detailedCookie("JSESSIONID");
            assertThat(cookie.getSameSite()).isEqualTo("None");
            assertThat(cookie.isHttpOnly()).isTrue();
            assertThat(cookie.isSecured()).isFalse();
        }

        @Test
        void 아이디가_없으면_로그인_실패() {
            // given
            AdminLoginRequest request = new AdminLoginRequest("1234", "admin");

            // when
            var 응답 = 어드민_로그인_요청(request);

            // then
            응답_상태를_검증한다(응답, 찾을수_없음);
            예외_메세지를_검증한다(응답, "해당 계정정보를 가진 어드민 계정이 존재하지 않습니다.");
        }

        @Test
        void 비밀번호가_다르면_로그인_실패() {
            // given
            // given
            AdminLoginRequest request = new AdminLoginRequest("admin", "1234");

            // when
            var 응답 = 어드민_로그인_요청(request);

            // then
            응답_상태를_검증한다(응답, 인증되지_않음);
            예외_메세지를_검증한다(응답, "비밀번호가 일치하지 않습니다.");
        }
    }
}

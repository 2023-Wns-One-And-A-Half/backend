package com.oneandahalf.backend.acceptance.admin.auth;


import static com.oneandahalf.backend.acceptance.AcceptanceSteps.given;

import com.oneandahalf.backend.acceptance.AcceptanceTest;
import com.oneandahalf.backend.admin.auth.presentation.request.AdminLoginRequest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
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
        void 로그인한다() {
            // given
            AdminLoginRequest request = new AdminLoginRequest("admin", "admin");

            // when
            ExtractableResponse<Response> 응답 = given()
                    .body(request)
                    .post("/admin/auth/login")
                    .then().log().all()
                    .extract();

            // then
        }
    }
}

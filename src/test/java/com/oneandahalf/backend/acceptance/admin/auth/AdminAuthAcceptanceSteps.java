package com.oneandahalf.backend.acceptance.admin.auth;


import static com.oneandahalf.backend.acceptance.AcceptanceSteps.given;

import com.oneandahalf.backend.admin.auth.presentation.request.AdminLoginRequest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

@SuppressWarnings("NonAsciiCharacters")
public class AdminAuthAcceptanceSteps {

    public static ExtractableResponse<Response> 어드민_로그인_요청(AdminLoginRequest request) {
        return given()
                .body(request)
                .post("/admin/auth/login")
                .then().log().all()
                .extract();
    }
}

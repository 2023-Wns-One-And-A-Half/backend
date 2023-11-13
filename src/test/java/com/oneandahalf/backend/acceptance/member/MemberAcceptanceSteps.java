package com.oneandahalf.backend.acceptance.member;

import static com.oneandahalf.backend.acceptance.AcceptanceSteps.given;

import com.oneandahalf.backend.member.presentation.request.SignupRequest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

@SuppressWarnings("NonAsciiCharacters")
public class MemberAcceptanceSteps {

    public static ExtractableResponse<Response> 회원가입_요청(SignupRequest request) {
        return given()
                .body(request)
                .post("/members")
                .then().log().all()
                .extract();
    }
}

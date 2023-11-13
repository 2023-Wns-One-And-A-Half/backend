package com.oneandahalf.backend.acceptance.member;

import static com.oneandahalf.backend.acceptance.AcceptanceSteps.given;

import com.oneandahalf.backend.member.presentation.request.LoginRequest;
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

    public static ExtractableResponse<Response> 로그인_요청(String 아이디, String 비밀번호) {
        return given()
                .body(new LoginRequest(아이디, 비밀번호))
                .post("/members/login")
                .then().log().all()
                .extract();

    }
}

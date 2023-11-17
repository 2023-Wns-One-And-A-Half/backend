package com.oneandahalf.backend.acceptance.member;

import static com.oneandahalf.backend.acceptance.AcceptanceSteps.given;
import static com.oneandahalf.backend.acceptance.AcceptanceSteps.멀티파트_이미지;
import static java.nio.charset.StandardCharsets.UTF_8;

import com.oneandahalf.backend.member.presentation.request.LoginRequest;
import com.oneandahalf.backend.member.presentation.request.SignupRequest;
import com.oneandahalf.backend.member.presentation.response.LoginResponse;
import io.restassured.builder.MultiPartSpecBuilder;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.MultiPartSpecification;
import io.restassured.specification.RequestSpecification;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

@SuppressWarnings("NonAsciiCharacters")
public class MemberAcceptanceSteps {

    public static ExtractableResponse<Response> 회원가입_요청(SignupRequest request) {
        var username = new MultiPartSpecBuilder(request.username())
                .controlName("username")
                .charset(UTF_8)
                .build();
        var password = new MultiPartSpecBuilder(request.password())
                .controlName("password")
                .charset(UTF_8)
                .build();
        var activityArea = new MultiPartSpecBuilder(request.activityArea())
                .controlName("activityArea")
                .charset(UTF_8)
                .build();
        var nickname = new MultiPartSpecBuilder(request.nickname())
                .controlName("nickname")
                .charset(UTF_8)
                .build();
        RequestSpecification requestSpecification = given()
                .multiPart(username)
                .multiPart(password)
                .multiPart(activityArea)
                .multiPart(nickname)
                .multiPart(멀티파트_이미지(request.profileImage(), "profileImage"));
        return requestSpecification
                .contentType("multipart/form-data")
                .when()
                .post("/members")
                .then().log().all()
                .extract();
    }

    public static String 로그인_후_세션_추출(String 아이디, String 비밀번호) {
        return 로그인_요청(아이디, 비밀번호)
                .as(LoginResponse.class)
                .jsessionid();
    }

    public static ExtractableResponse<Response> 로그인_요청(String 아이디, String 비밀번호) {
        return given()
                .body(new LoginRequest(아이디, 비밀번호))
                .post("/members/login")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 내_정보_조회_요청(String 세션) {
        return given(세션)
                .get("/members/my")
                .then().log().all()
                .extract();
    }
}

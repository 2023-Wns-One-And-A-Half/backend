package com.oneandahalf.backend.acceptance.notification;

import static com.oneandahalf.backend.acceptance.AcceptanceSteps.given;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

@SuppressWarnings("NonAsciiCharacters")
public class NotificationAcceptanceSteps {

    public static ExtractableResponse<Response> 내_알림_목록_조회_요청(String 말랑_세션) {
        return given(말랑_세션)
                .get("/notifications/my")
                .then().log().all()
                .extract();
    }

    public static void 알림_읽음_요청(String 말랑_세션, Long 알림_ID) {
        given(말랑_세션)
                .post("/notifications/read/{id}", 알림_ID)
                .then().log().all()
                .extract();
    }
}

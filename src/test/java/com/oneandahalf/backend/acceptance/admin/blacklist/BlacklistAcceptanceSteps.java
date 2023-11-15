package com.oneandahalf.backend.acceptance.admin.blacklist;

import static com.oneandahalf.backend.acceptance.AcceptanceSteps.given;

import com.oneandahalf.backend.admin.blacklist.presentation.request.AddBlacklistRequest;
import com.oneandahalf.backend.admin.blacklist.presentation.request.DeleteBlacklistRequest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

@SuppressWarnings("NonAsciiCharacters")
public class BlacklistAcceptanceSteps {

    public static ExtractableResponse<Response> 블랙리스트_추가_요청(String 어드민_세션, Long 회원_ID) {
        return given(어드민_세션)
                .body(new AddBlacklistRequest(회원_ID))
                .post("/admin/blacklists")
                .then()
                .log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 블랙리스트_제거_요청(String 어드민_세션, Long 회원_ID) {
        return given(어드민_세션)
                .body(new DeleteBlacklistRequest(회원_ID))
                .delete("/admin/blacklists")
                .then()
                .log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 블랙리스트_목록_조회_요청(String 어드민_세션) {
        return given(어드민_세션)
                .get("/admin/blacklists")
                .then()
                .log().all()
                .extract();
    }
}

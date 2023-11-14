package com.oneandahalf.backend.acceptance.keyword;

import static com.oneandahalf.backend.acceptance.AcceptanceSteps.given;

import com.oneandahalf.backend.keyword.presentation.request.CreateKeywordRequest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

@SuppressWarnings("NonAsciiCharacters")
public class KeywordAcceptanceSteps {

    public static ExtractableResponse<Response> 키워드_작성_요청(
            String 세션,
            String 키워드
    ) {
        return given(세션)
                .body(new CreateKeywordRequest(키워드))
                .post("/keywords")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 키워드_삭제_요청(
            String 세션,
            Long 키워드_ID
    ) {
        return given(세션)
                .delete("/keywords/{id}", 키워드_ID)
                .then().log().all()
                .extract();
    }
}

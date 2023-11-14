package com.oneandahalf.backend.acceptance.comment;

import static com.oneandahalf.backend.acceptance.AcceptanceSteps.given;

import com.oneandahalf.backend.comment.presentation.request.WriteCommentRequest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

@SuppressWarnings("NonAsciiCharacters")
public class CommentAcceptanceSteps {

    public static ExtractableResponse<Response> 댓글_작성_요청(String 말랑_세션, Long 상품_ID, String 내용) {
        return given(말랑_세션)
                .body(new WriteCommentRequest(상품_ID, 내용))
                .post("/comments")
                .then()
                .log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 댓글_조회_요청(Long 상품_ID) {
        return given()
                .queryParam("productId", 상품_ID)
                .get("/comments")
                .then()
                .log().all()
                .extract();
    }
}

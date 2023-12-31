package com.oneandahalf.backend.acceptance.trade;

import static com.oneandahalf.backend.acceptance.AcceptanceSteps.given;

import com.oneandahalf.backend.trade.presentation.request.SuggestTradeRequest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

@SuppressWarnings("NonAsciiCharacters")
public class TradeSuggestionAcceptanceSteps {

    public static ExtractableResponse<Response> 거래_제안_요청(String 말랑_세션, Long 상품_ID) {
        return given(말랑_세션)
                .body(new SuggestTradeRequest(상품_ID))
                .post("/trade-suggests")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 거래_제안_상태_조회_요청(String 말랑_세션, Long 상품_ID) {
        return given(말랑_세션)
                .queryParam("productId", 상품_ID)
                .get("/trade-suggests/exist")
                .then()
                .log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 거래_제안_조회_요청(String 세션, Long 상품_ID) {
        return given(세션)
                .queryParam("productId", 상품_ID)
                .get("/trade-suggests")
                .then()
                .log().all()
                .extract();
    }
}

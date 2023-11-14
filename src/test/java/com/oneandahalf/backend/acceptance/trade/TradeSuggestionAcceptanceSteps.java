package com.oneandahalf.backend.acceptance.trade;

import static com.oneandahalf.backend.acceptance.AcceptanceSteps.given;

import com.oneandahalf.backend.trade.presentation.request.SuggestTradeRequest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

@SuppressWarnings("NonAsciiCharacters")
public class TradeSuggestionAcceptanceSteps {

    public static ExtractableResponse<Response> 거래_제안_요청(String 말랑_세션, Long 상품1_ID) {
        return given(말랑_세션)
                .body(new SuggestTradeRequest(상품1_ID))
                .post("/trade-suggests")
                .then().log().all()
                .extract();
    }
}

package com.oneandahalf.backend.acceptance.trade;

import static com.oneandahalf.backend.acceptance.AcceptanceSteps.given;

import com.oneandahalf.backend.trade.presentation.request.ConfirmTradeRequest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

@SuppressWarnings("NonAsciiCharacters")
public class TradeAcceptanceSteps {

    public static ExtractableResponse<Response> 거래_확정_요청(String 세션, Long 거래_제안_ID) {
        return given(세션)
                .body(new ConfirmTradeRequest(거래_제안_ID))
                .post("/trades")
                .then()
                .log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 내가_구매한_상품_조회_요청(String 세션) {
        return given(세션)
                .get("/trades/purchased")
                .then()
                .log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 내가_판매한_상품_조회_요청(String 세션) {
        return given(세션)
                .get("/trades/sold")
                .then()
                .log().all()
                .extract();
    }
}

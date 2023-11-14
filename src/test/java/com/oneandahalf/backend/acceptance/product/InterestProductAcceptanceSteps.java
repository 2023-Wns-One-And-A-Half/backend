package com.oneandahalf.backend.acceptance.product;

import static com.oneandahalf.backend.acceptance.AcceptanceSteps.given;

import com.oneandahalf.backend.product.presentation.request.InterestProductRequest;
import com.oneandahalf.backend.product.presentation.request.UnInterestProductRequest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

@SuppressWarnings("NonAsciiCharacters")
public class InterestProductAcceptanceSteps {

    public static ExtractableResponse<Response> 관심_상품_등록_요청(String 세션, Long 상품_ID) {
        return given(세션)
                .body(new InterestProductRequest(상품_ID))
                .post("/interest-products")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 관심_상품_취소_요청(String 세션, Long 상품_ID) {
        return given(세션)
                .body(new UnInterestProductRequest(상품_ID))
                .delete("/interest-products")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 내_관심_상품_목록_조회_요청(String 세션) {
        return given(세션)
                .get("/interest-products/my")
                .then().log().all()
                .extract();
    }
}

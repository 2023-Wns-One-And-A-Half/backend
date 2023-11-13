package com.oneandahalf.backend.acceptance.product;

import static com.oneandahalf.backend.acceptance.AcceptanceSteps.given;

import com.oneandahalf.backend.product.presentation.request.RegisterProductRequest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

@SuppressWarnings("NonAsciiCharacters")
public class ProductAcceptanceSteps {

    public static ExtractableResponse<Response> 상품_등록_요청(String 세션, RegisterProductRequest request) {
        return given(세션)
                .body(request)
                .post("/products")
                .then().log().all()
                .extract();
    }
}

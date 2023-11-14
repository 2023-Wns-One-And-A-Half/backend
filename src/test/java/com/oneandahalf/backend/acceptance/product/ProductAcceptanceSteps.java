package com.oneandahalf.backend.acceptance.product;

import static com.oneandahalf.backend.acceptance.AcceptanceSteps.given;

import com.oneandahalf.backend.member.domain.ActivityArea;
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

    public static ExtractableResponse<Response> 상품_상세_조회_요청(Long 상품_ID) {
        return given()
                .get("/products/{id}", 상품_ID)
                .then().log()
                .all()
                .extract();
    }

    public static ExtractableResponse<Response> 상품_검색_요청(
            ActivityArea 지역,
            Integer 최소가격,
            Integer 최대가격,
            String 이름
    ) {
        return given()
                .queryParam("name", 이름)
                .queryParam("activityArea", 지역)
                .queryParam("minPrice", 최소가격)
                .queryParam("maxPrice", 최대가격)
                .get("/products")
                .then().log().all()
                .extract();
    }
}

package com.oneandahalf.backend.acceptance.product;

import static com.oneandahalf.backend.acceptance.AcceptanceSteps.given;
import static com.oneandahalf.backend.acceptance.AcceptanceSteps.멀티파트_이미지;
import static java.nio.charset.StandardCharsets.UTF_8;

import com.oneandahalf.backend.member.domain.ActivityArea;
import com.oneandahalf.backend.product.presentation.request.RegisterProductRequest;
import io.restassured.builder.MultiPartSpecBuilder;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

@SuppressWarnings("NonAsciiCharacters")
public class ProductAcceptanceSteps {

    public static ExtractableResponse<Response> 상품_등록_요청(String 세션, RegisterProductRequest request) {
        var name = new MultiPartSpecBuilder(request.name())
                .controlName("name")
                .charset(UTF_8)
                .build();
        var description = new MultiPartSpecBuilder(request.description())
                .controlName("description")
                .charset(UTF_8)
                .build();
        var price = new MultiPartSpecBuilder(request.price())
                .controlName("price")
                .charset(UTF_8)
                .build();
        RequestSpecification requestSpecification = given(세션)
                .multiPart(name)
                .multiPart(description)
                .multiPart(price);
        request.productImages().forEach(it -> {
            requestSpecification.multiPart(멀티파트_이미지(it, "productImages"));
        });
        return requestSpecification
                .contentType("multipart/form-data")
                .when()
                .post("/products")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 상품_상세_조회_요청(String 세션, Long 상품_ID) {
        return 상품_상세_조회_요청(세션, 상품_ID, null);
    }

    public static ExtractableResponse<Response> 상품_상세_조회_요청(String 세션, Long 상품_ID, String 조회수_세션_쿠키) {
        RequestSpecification given = given(세션);
        if (조회수_세션_쿠키 != null) {
            given = given.cookie("VIEW_SESSION", 조회수_세션_쿠키);
        }
        return given
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

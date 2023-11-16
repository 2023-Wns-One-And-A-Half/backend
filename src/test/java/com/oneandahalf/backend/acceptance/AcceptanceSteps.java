package com.oneandahalf.backend.acceptance;

import static io.restassured.http.ContentType.JSON;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;

import com.oneandahalf.backend.common.exception.ErrorCode;
import io.restassured.RestAssured;
import io.restassured.builder.MultiPartSpecBuilder;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.MultiPartSpecification;
import io.restassured.specification.RequestSpecification;
import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

@SuppressWarnings("NonAsciiCharacters")
public final class AcceptanceSteps {

    public static final HttpStatus 정상_처리 = HttpStatus.OK;
    public static final HttpStatus 생성됨 = HttpStatus.CREATED;
    public static final HttpStatus 본문_없음 = HttpStatus.NO_CONTENT;
    public static final HttpStatus 잘못된_요청 = HttpStatus.BAD_REQUEST;
    public static final HttpStatus 인증되지_않음 = HttpStatus.UNAUTHORIZED;
    public static final HttpStatus 권한_없음 = HttpStatus.FORBIDDEN;
    public static final HttpStatus 찾을수_없음 = HttpStatus.NOT_FOUND;
    public static final HttpStatus 중복됨 = HttpStatus.CONFLICT;

    public static MultipartFile multipartFile(String name) {
        return new MockMultipartFile(
                name, name, "multipart/form-data", name.getBytes()
        );
    }

    public static MultiPartSpecification 멀티파트_이미지(MultipartFile image, String name) {
        try {
            return new MultiPartSpecBuilder(image.getBytes())
                    .controlName(name)
                    .fileName(image.getName())
                    .charset(UTF_8)
                    .build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T 없음() {
        return null;
    }

    public static RequestSpecification given() {
        return given(null);
    }

    public static RequestSpecification given(String 세션) {
        return RestAssured
                .given().log().all()
                .cookie("JSESSIONID", 세션)
                .contentType(JSON);
    }

    public static void 응답_상태를_검증한다(
            ExtractableResponse<Response> 응답,
            HttpStatus 예상_상태
    ) {
        assertThat(응답.statusCode()).isEqualTo(예상_상태.value());
    }

    public static Long ID를_추출한다(
            ExtractableResponse<Response> 응답
    ) {
        String location = 응답.header("Location");
        return Long.valueOf(location.substring(location.lastIndexOf("/") + 1));
    }

    public static void 값이_존재한다(Object o) {
        assertThat(o).isNotNull();
    }

    public static void 값이_없다(Object o) {
        assertThat(o).isNull();
    }

    public static void 예외_메세지를_검증한다(
            ExtractableResponse<Response> 응답,
            String 메세지
    ) {
        ErrorCode errorCode = 응답.as(ErrorCode.class);
        assertThat(errorCode.message()).isEqualTo(메세지);
    }
}

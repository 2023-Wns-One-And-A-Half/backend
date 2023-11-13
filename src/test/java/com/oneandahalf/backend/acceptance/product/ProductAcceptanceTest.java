package com.oneandahalf.backend.acceptance.product;

import static com.oneandahalf.backend.acceptance.AcceptanceSteps.ID를_추출한다;
import static com.oneandahalf.backend.acceptance.AcceptanceSteps.값이_존재한다;
import static com.oneandahalf.backend.acceptance.AcceptanceSteps.생성됨;
import static com.oneandahalf.backend.acceptance.AcceptanceSteps.예외_메세지를_검증한다;
import static com.oneandahalf.backend.acceptance.AcceptanceSteps.응답_상태를_검증한다;
import static com.oneandahalf.backend.acceptance.AcceptanceSteps.인증되지_않음;
import static com.oneandahalf.backend.acceptance.AcceptanceSteps.잘못된_요청;
import static com.oneandahalf.backend.acceptance.member.MemberAcceptanceSteps.로그인_후_세션_추출;
import static com.oneandahalf.backend.acceptance.member.MemberAcceptanceSteps.회원가입_요청;
import static com.oneandahalf.backend.acceptance.product.ProductAcceptanceSteps.상품_검색_요청;
import static com.oneandahalf.backend.acceptance.product.ProductAcceptanceSteps.상품_등록_요청;
import static com.oneandahalf.backend.member.domain.ActivityArea.INCHEON;
import static com.oneandahalf.backend.member.domain.ActivityArea.SEOUL;
import static org.assertj.core.api.Assertions.assertThat;

import com.oneandahalf.backend.acceptance.AcceptanceTest;
import com.oneandahalf.backend.common.page.PageResponse;
import com.oneandahalf.backend.member.domain.ActivityArea;
import com.oneandahalf.backend.member.presentation.request.SignupRequest;
import com.oneandahalf.backend.product.presentation.request.RegisterProductRequest;
import com.oneandahalf.backend.product.query.response.ProductSearchResponse;
import io.restassured.common.mapper.TypeRef;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("상품 인수테스트")
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
public class ProductAcceptanceTest {

    private final SignupRequest 회원가입_정보 = SignupRequest.builder()
            .username("mallang1234")
            .password("mallang12345!@#")
            .nickname("mallang")
            .activityArea(SEOUL)
            .profileImageName("mallangImage")
            .build();

    @Nested
    class 상품_등록_API extends AcceptanceTest {

        @Test
        void 상품을_등록한다() {
            // given
            회원가입_요청(회원가입_정보);
            var 세션 = 로그인_후_세션_추출("mallang1234", "mallang12345!@#");
            RegisterProductRequest request = RegisterProductRequest.builder()
                    .name("말랑이")
                    .description("말랑말랑 말랑이")
                    .price(10_000)
                    .productImageNames(List.of("말랑이_사진1", "말랑이_사진2"))
                    .build();

            // when
            var 응답 = 상품_등록_요청(세션, request);

            // then
            응답_상태를_검증한다(응답, 생성됨);
            값이_존재한다(ID를_추출한다(응답));
        }


        @Test
        void 인증되지_않았으면_등록할_수_없다() {
            // given
            RegisterProductRequest request = RegisterProductRequest.builder()
                    .name("말랑이")
                    .description("말랑말랑 말랑이")
                    .price(10_000)
                    .productImageNames(List.of("말랑이_사진1", "말랑이_사진2"))
                    .build();

            // when
            var 응답 = 상품_등록_요청(null, request);

            // then
            응답_상태를_검증한다(응답, 인증되지_않음);
        }

        @Test
        void 가격이_0원_미만이면_예외() {
            // given
            회원가입_요청(회원가입_정보);
            var 세션 = 로그인_후_세션_추출("mallang1234", "mallang12345!@#");
            RegisterProductRequest request = RegisterProductRequest.builder()
                    .name("말랑이")
                    .description("말랑말랑 말랑이")
                    .price(-1)
                    .productImageNames(List.of("말랑이_사진1", "말랑이_사진2"))
                    .build();

            // when
            var 응답 = 상품_등록_요청(세션, request);

            // then
            응답_상태를_검증한다(응답, 잘못된_요청);
            예외_메세지를_검증한다(응답, "가격은 0원 이상이어야 합니다.");
        }
    }

    @Nested
    class 상품_검색_API extends AcceptanceTest {

        @Test
        void 이름_포함조건으로_검색한다() {
            // given
            회원가입_요청(회원가입_정보);
            var 세션 = 로그인_후_세션_추출("mallang1234", "mallang12345!@#");
            RegisterProductRequest request1 = RegisterProductRequest.builder()
                    .name("말랑이")
                    .description("말랑말랑 말랑이")
                    .price(10_000)
                    .productImageNames(List.of("말랑이_사진1", "말랑이_사진2"))
                    .build();
            RegisterProductRequest request2 = RegisterProductRequest.builder()
                    .name("몰랑이")
                    .description("말랑말랑 말랑이")
                    .price(20_000)
                    .productImageNames(List.of("몰랑이_사진1", "몰랑이_사진2"))
                    .build();

            var 말랑이_ID = ID를_추출한다(상품_등록_요청(세션, request1));
            var 몰랑이_ID = ID를_추출한다(상품_등록_요청(세션, request2));

            // when
            var 응답 = 상품_검색_요청(null, null, null, "말랑");

            // then
            PageResponse<ProductSearchResponse> responses = 응답.as(new TypeRef<>() {
            });
            assertThat(responses.content())
                    .extracting(ProductSearchResponse::id)
                    .containsExactly(말랑이_ID);
        }

        @Test
        void 지역_포함조건으로_검색한다() {
            // given
            회원가입_요청(회원가입_정보);
            var 세션 = 로그인_후_세션_추출("mallang1234", "mallang12345!@#");
            RegisterProductRequest request1 = RegisterProductRequest.builder()
                    .name("말랑이")
                    .description("말랑말랑 말랑이")
                    .price(10_000)
                    .productImageNames(List.of("말랑이_사진1", "말랑이_사진2"))
                    .build();
            RegisterProductRequest request2 = RegisterProductRequest.builder()
                    .name("몰랑이")
                    .description("말랑말랑 말랑이")
                    .price(20_000)
                    .productImageNames(List.of("몰랑이_사진1", "몰랑이_사진2"))
                    .build();

            var 말랑이_ID = ID를_추출한다(상품_등록_요청(세션, request1));
            var 몰랑이_ID = ID를_추출한다(상품_등록_요청(세션, request2));

            // when
            var 응답 = 상품_검색_요청(INCHEON, null, null, null);

            // then
            PageResponse<ProductSearchResponse> responses = 응답.as(new TypeRef<>() {
            });
            assertThat(responses.content()).isEmpty();
        }

        @Test
        void 가격_조건_검색() {
            // given
            회원가입_요청(회원가입_정보);
            var 세션 = 로그인_후_세션_추출("mallang1234", "mallang12345!@#");
            RegisterProductRequest request1 = RegisterProductRequest.builder()
                    .name("말랑이")
                    .description("말랑말랑 말랑이")
                    .price(10_000)
                    .productImageNames(List.of("말랑이_사진1", "말랑이_사진2"))
                    .build();
            RegisterProductRequest request2 = RegisterProductRequest.builder()
                    .name("몰랑이")
                    .description("말랑말랑 말랑이")
                    .price(20_000)
                    .productImageNames(List.of("몰랑이_사진1", "몰랑이_사진2"))
                    .build();

            var 말랑이_ID = ID를_추출한다(상품_등록_요청(세션, request1));
            var 몰랑이_ID = ID를_추출한다(상품_등록_요청(세션, request2));

            // when
            var 응답1 = 상품_검색_요청(SEOUL, 11_111, null, null);
            var 응답2 = 상품_검색_요청(null, 11, 22_111, null);
            var 응답3 = 상품_검색_요청(null, null, 19999, null);

            // then
            PageResponse<ProductSearchResponse> responses1 = 응답1.as(new TypeRef<>() {
            });
            PageResponse<ProductSearchResponse> responses2 = 응답2.as(new TypeRef<>() {
            });
            PageResponse<ProductSearchResponse> responses3 = 응답3.as(new TypeRef<>() {
            });
            assertThat(responses1.content())
                    .extracting(ProductSearchResponse::id)
                    .containsExactly(몰랑이_ID);
            assertThat(responses2.content())
                    .extracting(ProductSearchResponse::id)
                    .containsExactly(몰랑이_ID, 말랑이_ID);
            assertThat(responses3.content())
                    .extracting(ProductSearchResponse::id)
                    .containsExactly(말랑이_ID);
        }
    }
}

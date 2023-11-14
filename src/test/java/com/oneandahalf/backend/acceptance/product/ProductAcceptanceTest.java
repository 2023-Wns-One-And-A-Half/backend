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
import static com.oneandahalf.backend.acceptance.product.InterestProductAcceptanceSteps.관심_상품_등록_요청;
import static com.oneandahalf.backend.acceptance.product.ProductAcceptanceSteps.상품_검색_요청;
import static com.oneandahalf.backend.acceptance.product.ProductAcceptanceSteps.상품_등록_요청;
import static com.oneandahalf.backend.acceptance.product.ProductAcceptanceSteps.상품_상세_조회_요청;
import static com.oneandahalf.backend.acceptance.trade.TradeAcceptanceSteps.거래_확정_요청;
import static com.oneandahalf.backend.acceptance.trade.TradeSuggestionAcceptanceSteps.거래_제안_요청;
import static com.oneandahalf.backend.member.domain.ActivityArea.INCHEON;
import static com.oneandahalf.backend.member.domain.ActivityArea.SEOUL;
import static org.assertj.core.api.Assertions.assertThat;

import com.oneandahalf.backend.acceptance.AcceptanceTest;
import com.oneandahalf.backend.common.page.PageResponse;
import com.oneandahalf.backend.member.presentation.request.SignupRequest;
import com.oneandahalf.backend.product.presentation.request.RegisterProductRequest;
import com.oneandahalf.backend.product.query.response.ProductDetailResponse;
import com.oneandahalf.backend.product.query.response.ProductDetailResponse.SellerInfoResponse;
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
    class 상품_상세_조회_API extends AcceptanceTest {

        private final RegisterProductRequest 상품1_정보 = RegisterProductRequest.builder()
                .name("말랑이")
                .description("말랑말랑 말랑이")
                .price(10_000)
                .productImageNames(List.of("말랑이_사진1", "말랑이_사진2"))
                .build();

        @Test
        void 상품의_상세정보를_조회한다() {
            // given
            var 회원_ID = ID를_추출한다(회원가입_요청(회원가입_정보));
            var 세션 = 로그인_후_세션_추출("mallang1234", "mallang12345!@#");
            var 상품_ID = ID를_추출한다(상품_등록_요청(세션, 상품1_정보));
            관심_상품_등록_요청(세션, 상품_ID);

            // when
            var 응답 = 상품_상세_조회_요청(null, 상품_ID);

            // then
            assertThat(응답.as(ProductDetailResponse.class))
                    .usingRecursiveComparison()
                    .isEqualTo(ProductDetailResponse.builder()
                            .id(상품_ID)
                            .name("말랑이")
                            .description("말랑말랑 말랑이")
                            .interestedCount(1)
                            .price(10_000)
                            .viewCount(1)
                            .traded(false)
                            .interested(false)
                            .productImageNames(List.of("말랑이_사진1", "말랑이_사진2"))
                            .sellerInfo(SellerInfoResponse.builder()
                                    .id(회원_ID)
                                    .nickname("mallang")
                                    .activityArea(SEOUL)
                                    .profileImageName("mallangImage")
                                    .build()
                            ).build());
        }

        @Test
        void 중복_조회_시_조회수는_오르지_않는다() {
            // given
            var 회원_ID = ID를_추출한다(회원가입_요청(회원가입_정보));
            var 세션 = 로그인_후_세션_추출("mallang1234", "mallang12345!@#");
            var 상품_ID = ID를_추출한다(상품_등록_요청(세션, 상품1_정보));
            String 상품_조회_세션 = 상품_상세_조회_요청(null, 상품_ID).cookie("VIEW_SESSION");

            // when
            var 응답 = 상품_상세_조회_요청(null, 상품_ID, 상품_조회_세션);

            // then
            assertThat(응답.as(ProductDetailResponse.class))
                    .usingRecursiveComparison()
                    .comparingOnlyFields("viewCount")
                    .isEqualTo(ProductDetailResponse.builder()
                            .viewCount(1)
                            .build()
                    );
        }

        @Test
        void 새로운_세션이라면_조회수는_오른다() {
            // given
            var 회원_ID = ID를_추출한다(회원가입_요청(회원가입_정보));
            var 세션 = 로그인_후_세션_추출("mallang1234", "mallang12345!@#");
            var 상품_ID = ID를_추출한다(상품_등록_요청(세션, 상품1_정보));
            상품_상세_조회_요청(null, 상품_ID);

            // when
            var 응답 = 상품_상세_조회_요청(null, 상품_ID);

            // then
            assertThat(응답.as(ProductDetailResponse.class))
                    .usingRecursiveComparison()
                    .comparingOnlyFields("viewCount")
                    .isEqualTo(ProductDetailResponse.builder()
                            .viewCount(2)
                            .build()
                    );
        }

        @Test
        void 로그인_후_요청_시_관심등록여부도_반환한다() {
            // given
            var 회원_ID = ID를_추출한다(회원가입_요청(회원가입_정보));
            var 세션 = 로그인_후_세션_추출("mallang1234", "mallang12345!@#");
            var 상품_ID = ID를_추출한다(상품_등록_요청(세션, 상품1_정보));
            관심_상품_등록_요청(세션, 상품_ID);

            // when
            var 응답 = 상품_상세_조회_요청(세션, 상품_ID);

            // then
            assertThat(응답.as(ProductDetailResponse.class))
                    .usingRecursiveComparison()
                    .isEqualTo(ProductDetailResponse.builder()
                            .id(상품_ID)
                            .name("말랑이")
                            .description("말랑말랑 말랑이")
                            .interestedCount(1)
                            .price(10_000)
                            .traded(false)
                            .viewCount(1)
                            .interested(true)
                            .productImageNames(List.of("말랑이_사진1", "말랑이_사진2"))
                            .sellerInfo(SellerInfoResponse.builder()
                                    .id(회원_ID)
                                    .nickname("mallang")
                                    .activityArea(SEOUL)
                                    .profileImageName("mallangImage")
                                    .build()
                            ).build());
        }

        @Test
        void 거래를_확정된_상품_조회_시() {
            // given
            회원가입_요청(회원가입_정보);
            var 동훈_ID = ID를_추출한다(회원가입_요청(SignupRequest.builder()
                    .username("donghun1234")
                    .password("donghun12345!@#")
                    .nickname("donghun")
                    .activityArea(SEOUL)
                    .profileImageName("donghunImage")
                    .build()));
            var 말랑_세션 = 로그인_후_세션_추출("mallang1234", "mallang12345!@#");
            var 동훈_세션 = 로그인_후_세션_추출("donghun1234", "donghun12345!@#");
            var 상품_ID = ID를_추출한다(상품_등록_요청(동훈_세션, 상품1_정보));
            var 거래_제안_ID = ID를_추출한다(거래_제안_요청(말랑_세션, 상품_ID));
            거래_확정_요청(동훈_세션, 거래_제안_ID);

            // when
            var 응답 = 상품_상세_조회_요청(말랑_세션, 상품_ID);

            // then
            assertThat(응답.as(ProductDetailResponse.class))
                    .usingRecursiveComparison()
                    .isEqualTo(ProductDetailResponse.builder()
                            .id(상품_ID)
                            .name("말랑이")
                            .description("말랑말랑 말랑이")
                            .interestedCount(0)
                            .price(10_000)
                            .interested(false)
                            .viewCount(1)
                            .traded(true)
                            .productImageNames(List.of("말랑이_사진1", "말랑이_사진2"))
                            .sellerInfo(SellerInfoResponse.builder()
                                    .id(동훈_ID)
                                    .nickname("donghun")
                                    .activityArea(SEOUL)
                                    .profileImageName("donghunImage")
                                    .build()
                            ).build());
        }
    }

    @Nested
    class 상품_검색_API extends AcceptanceTest {

        private final RegisterProductRequest 상품1_정보 = RegisterProductRequest.builder()
                .name("말랑이")
                .description("말랑말랑 말랑이")
                .price(10_000)
                .productImageNames(List.of("말랑이_사진1", "말랑이_사진2"))
                .build();
        private final RegisterProductRequest 상품2_정보 = RegisterProductRequest.builder()
                .name("몰랑이")
                .description("말랑말랑 말랑이")
                .price(20_000)
                .productImageNames(List.of("몰랑이_사진1", "몰랑이_사진2"))
                .build();

        @Test
        void 이름_포함조건으로_검색한다() {
            // given
            회원가입_요청(회원가입_정보);
            var 세션 = 로그인_후_세션_추출("mallang1234", "mallang12345!@#");

            var 말랑이_ID = ID를_추출한다(상품_등록_요청(세션, 상품1_정보));
            var 몰랑이_ID = ID를_추출한다(상품_등록_요청(세션, 상품2_정보));

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
            var 말랑이_ID = ID를_추출한다(상품_등록_요청(세션, 상품1_정보));
            var 몰랑이_ID = ID를_추출한다(상품_등록_요청(세션, 상품2_정보));

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
            var 말랑이_ID = ID를_추출한다(상품_등록_요청(세션, 상품1_정보
            ));
            var 몰랑이_ID = ID를_추출한다(상품_등록_요청(세션, 상품2_정보));

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

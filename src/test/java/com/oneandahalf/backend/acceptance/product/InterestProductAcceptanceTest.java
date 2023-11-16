package com.oneandahalf.backend.acceptance.product;

import static com.oneandahalf.backend.acceptance.AcceptanceSteps.ID를_추출한다;
import static com.oneandahalf.backend.acceptance.AcceptanceSteps.multipartFile;
import static com.oneandahalf.backend.acceptance.AcceptanceSteps.본문_없음;
import static com.oneandahalf.backend.acceptance.AcceptanceSteps.생성됨;
import static com.oneandahalf.backend.acceptance.AcceptanceSteps.응답_상태를_검증한다;
import static com.oneandahalf.backend.acceptance.AcceptanceSteps.인증되지_않음;
import static com.oneandahalf.backend.acceptance.AcceptanceSteps.중복됨;
import static com.oneandahalf.backend.acceptance.AcceptanceSteps.찾을수_없음;
import static com.oneandahalf.backend.acceptance.member.MemberAcceptanceSteps.로그인_후_세션_추출;
import static com.oneandahalf.backend.acceptance.member.MemberAcceptanceSteps.회원가입_요청;
import static com.oneandahalf.backend.acceptance.product.InterestProductAcceptanceSteps.관심_상품_등록_요청;
import static com.oneandahalf.backend.acceptance.product.InterestProductAcceptanceSteps.관심_상품_취소_요청;
import static com.oneandahalf.backend.acceptance.product.InterestProductAcceptanceSteps.내_관심_상품_목록_조회_요청;
import static com.oneandahalf.backend.acceptance.product.ProductAcceptanceSteps.상품_등록_요청;
import static com.oneandahalf.backend.member.domain.ActivityArea.SEOUL;
import static org.assertj.core.api.Assertions.assertThat;

import com.oneandahalf.backend.acceptance.AcceptanceTest;
import com.oneandahalf.backend.member.presentation.request.SignupRequest;
import com.oneandahalf.backend.product.presentation.request.RegisterProductRequest;
import com.oneandahalf.backend.product.query.response.MyInterestProductResponse;
import io.restassured.common.mapper.TypeRef;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("관심 상품 인수테스트")
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
public class InterestProductAcceptanceTest {

    private final SignupRequest 회원가입_정보 = SignupRequest.builder()
            .username("mallang1234")
            .password("mallang12345!@#")
            .nickname("mallang")
            .activityArea(SEOUL)
            .profileImage(multipartFile("mallangImage"))
            .build();

    private final RegisterProductRequest 상품1_정보 = RegisterProductRequest.builder()
            .name("말랑이")
            .description("말랑말랑 말랑이")
            .price(10_000)
            .productImages(List.of(multipartFile("말랑이_사진1"), multipartFile("말랑이_사진2")))
            .build();

    private final RegisterProductRequest 상품2_정보 = RegisterProductRequest.builder()
            .name("몰랑이")
            .description("몰랑몰랑 몰랑이")
            .price(20_000)
            .productImages(List.of(multipartFile("몰랑이_사진1")))
            .build();

    @Nested
    class 관심_상품_등록_API extends AcceptanceTest {

        @Test
        void 관심_상품으로_등록한다() {
            // given
            회원가입_요청(회원가입_정보);
            var 세션 = 로그인_후_세션_추출("mallang1234", "mallang12345!@#");
            var 상품1_ID = ID를_추출한다(상품_등록_요청(세션, 상품1_정보));

            // when
            var 응답 = 관심_상품_등록_요청(세션, 상품1_ID);

            // then
            응답_상태를_검증한다(응답, 생성됨);
        }

        @Test
        void 이미_관심_등록한_상품에_대해_또_등록하려는_경우_예외() {
            // given
            회원가입_요청(회원가입_정보);
            var 세션 = 로그인_후_세션_추출("mallang1234", "mallang12345!@#");
            var 상품1_ID = ID를_추출한다(상품_등록_요청(세션, 상품1_정보));
            관심_상품_등록_요청(세션, 상품1_ID);

            // when
            var 응답 = 관심_상품_등록_요청(세션, 상품1_ID);

            // then
            응답_상태를_검증한다(응답, 중복됨);
        }

        @Test
        void 인증되지_않았으면_오류() {
            // given
            회원가입_요청(회원가입_정보);
            var 세션 = 로그인_후_세션_추출("mallang1234", "mallang12345!@#");
            var 상품1_ID = ID를_추출한다(상품_등록_요청(세션, 상품1_정보));

            // when
            var 응답 = 관심_상품_등록_요청(null, 상품1_ID);

            // then
            응답_상태를_검증한다(응답, 인증되지_않음);
        }
    }

    @Nested
    class 관심_취소_API extends AcceptanceTest {

        @Test
        void 관심상품에서_제거한다() {
            // given
            회원가입_요청(회원가입_정보);
            var 세션 = 로그인_후_세션_추출("mallang1234", "mallang12345!@#");
            var 상품1_ID = ID를_추출한다(상품_등록_요청(세션, 상품1_정보));
            관심_상품_등록_요청(세션, 상품1_ID);

            // when
            var 응답 = 관심_상품_취소_요청(세션, 상품1_ID);

            // then
            응답_상태를_검증한다(응답, 본문_없음);
        }

        @Test
        void 관심상품으로_등록되지_않았다면_예외() {
            // given
            회원가입_요청(회원가입_정보);
            var 세션 = 로그인_후_세션_추출("mallang1234", "mallang12345!@#");
            var 상품1_ID = ID를_추출한다(상품_등록_요청(세션, 상품1_정보));

            // when
            var 응답 = 관심_상품_취소_요청(세션, 상품1_ID);

            // then
            응답_상태를_검증한다(응답, 찾을수_없음);
        }
    }

    @Nested
    class 내_관심_내역_조회_API extends AcceptanceTest {

        @Test
        void 내_관심_목록을_조회한다() {
            // given
            회원가입_요청(회원가입_정보);
            var 세션 = 로그인_후_세션_추출("mallang1234", "mallang12345!@#");
            var 상품1_ID = ID를_추출한다(상품_등록_요청(세션, 상품1_정보));
            var 상품2_ID = ID를_추출한다(상품_등록_요청(세션, 상품2_정보));
            관심_상품_등록_요청(세션, 상품1_ID);
            관심_상품_등록_요청(세션, 상품2_ID);

            // when
            var 응답 = 내_관심_상품_목록_조회_요청(세션);

            // then
            List<MyInterestProductResponse> responses = 응답.as(new TypeRef<>() {
            });
            assertThat(responses).hasSize(2);

        }

        @Test
        void 인증되지_않았다면_예외() {
            // given
            회원가입_요청(회원가입_정보);
            var 세션 = 로그인_후_세션_추출("mallang1234", "mallang12345!@#");
            var 상품1_ID = ID를_추출한다(상품_등록_요청(세션, 상품1_정보));
            var 상품2_ID = ID를_추출한다(상품_등록_요청(세션, 상품2_정보));
            관심_상품_등록_요청(세션, 상품1_ID);
            관심_상품_등록_요청(세션, 상품2_ID);

            // when
            var 응답 = 내_관심_상품_목록_조회_요청(null);

            // then
            응답_상태를_검증한다(응답, 인증되지_않음);
        }
    }
}

package com.oneandahalf.backend.acceptance.trade;

import static com.oneandahalf.backend.acceptance.AcceptanceSteps.ID를_추출한다;
import static com.oneandahalf.backend.acceptance.AcceptanceSteps.생성됨;
import static com.oneandahalf.backend.acceptance.AcceptanceSteps.예외_메세지를_검증한다;
import static com.oneandahalf.backend.acceptance.AcceptanceSteps.응답_상태를_검증한다;
import static com.oneandahalf.backend.acceptance.AcceptanceSteps.인증되지_않음;
import static com.oneandahalf.backend.acceptance.AcceptanceSteps.잘못된_요청;
import static com.oneandahalf.backend.acceptance.AcceptanceSteps.중복됨;
import static com.oneandahalf.backend.acceptance.member.MemberAcceptanceSteps.로그인_후_세션_추출;
import static com.oneandahalf.backend.acceptance.member.MemberAcceptanceSteps.회원가입_요청;
import static com.oneandahalf.backend.acceptance.product.ProductAcceptanceSteps.상품_등록_요청;
import static com.oneandahalf.backend.acceptance.trade.TradeSuggestionAcceptanceSteps.거래_제안_상태_조회_요청;
import static com.oneandahalf.backend.acceptance.trade.TradeSuggestionAcceptanceSteps.거래_제안_요청;
import static com.oneandahalf.backend.member.domain.ActivityArea.SEOUL;
import static org.assertj.core.api.Assertions.assertThat;

import com.oneandahalf.backend.acceptance.AcceptanceTest;
import com.oneandahalf.backend.member.presentation.request.SignupRequest;
import com.oneandahalf.backend.product.presentation.request.RegisterProductRequest;
import com.oneandahalf.backend.trade.query.response.TradeSuggestionStatusResponse;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("거래 제안 인수테스트")
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
public class TradeSuggestionAcceptanceTest {

    private final SignupRequest 말랑_회원가입_정보 = SignupRequest.builder()
            .username("mallang1234")
            .password("mallang12345!@#")
            .nickname("mallang")
            .activityArea(SEOUL)
            .profileImageName("mallangImage")
            .build();

    private final SignupRequest 동훈_회원가입_정보 = SignupRequest.builder()
            .username("donghun1234")
            .password("donghun12345!@#")
            .nickname("donghun")
            .activityArea(SEOUL)
            .profileImageName("donghunImage")
            .build();

    private final RegisterProductRequest 상품1_정보 = RegisterProductRequest.builder()
            .name("말랑이")
            .description("말랑말랑 말랑이")
            .price(10_000)
            .productImageNames(List.of("말랑이_사진1", "말랑이_사진2"))
            .build();

    @Nested
    class 거래_제안_API extends AcceptanceTest {

        @Test
        void 거래를_제안한다() {
            // given
            회원가입_요청(말랑_회원가입_정보);
            회원가입_요청(동훈_회원가입_정보);
            var 말랑_세션 = 로그인_후_세션_추출("mallang1234", "mallang12345!@#");
            var 동훈_세션 = 로그인_후_세션_추출("donghun1234", "donghun12345!@#");
            var 상품1_ID = ID를_추출한다(상품_등록_요청(동훈_세션, 상품1_정보));

            // when
            var 응답 = 거래_제안_요청(말랑_세션, 상품1_ID);

            // then
            응답_상태를_검증한다(응답, 생성됨);
        }

        @Test
        void 이미_제안한_경우_중복으로_요청을_보내면_예외() {
            // given
            회원가입_요청(말랑_회원가입_정보);
            회원가입_요청(동훈_회원가입_정보);
            var 말랑_세션 = 로그인_후_세션_추출("mallang1234", "mallang12345!@#");
            var 동훈_세션 = 로그인_후_세션_추출("donghun1234", "donghun12345!@#");
            var 상품1_ID = ID를_추출한다(상품_등록_요청(동훈_세션, 상품1_정보));
            거래_제안_요청(말랑_세션, 상품1_ID);

            // when
            var 응답 = 거래_제안_요청(말랑_세션, 상품1_ID);

            // then
            응답_상태를_검증한다(응답, 중복됨);
            예외_메세지를_검증한다(응답, "이미 구매요청한 상품입니다.");
        }

        @Test
        void 자신의_상품에_거래_제안을_하는_경우_예외() {
            // given
            회원가입_요청(동훈_회원가입_정보);
            var 말랑_세션 = 로그인_후_세션_추출("mallang1234", "mallang12345!@#");
            var 동훈_세션 = 로그인_후_세션_추출("donghun1234", "donghun12345!@#");
            var 상품1_ID = ID를_추출한다(상품_등록_요청(동훈_세션, 상품1_정보));
            거래_제안_요청(말랑_세션, 상품1_ID);

            // when
            var 응답 = 거래_제안_요청(동훈_세션, 상품1_ID);

            // then
            응답_상태를_검증한다(응답, 잘못된_요청);
            예외_메세지를_검증한다(응답, "자신이 판매하는 상품에 구매요청을 할 수 없습니다.");
        }

        @Test
        void 인증되지_않은경우_예외() {
            // given
            회원가입_요청(동훈_회원가입_정보);
            var 동훈_세션 = 로그인_후_세션_추출("donghun1234", "donghun12345!@#");
            var 상품1_ID = ID를_추출한다(상품_등록_요청(동훈_세션, 상품1_정보));

            // when
            var 응답 = 거래_제안_요청(null, 상품1_ID);

            // then
            응답_상태를_검증한다(응답, 인증되지_않음);
        }
    }

    @Nested
    class 거래_제안_상태_조회_API extends AcceptanceTest {

        @Test
        void 거래_제안을_한_경우() {
            // given
            회원가입_요청(말랑_회원가입_정보);
            회원가입_요청(동훈_회원가입_정보);
            var 말랑_세션 = 로그인_후_세션_추출("mallang1234", "mallang12345!@#");
            var 동훈_세션 = 로그인_후_세션_추출("donghun1234", "donghun12345!@#");
            var 상품1_ID = ID를_추출한다(상품_등록_요청(동훈_세션, 상품1_정보));
            거래_제안_요청(말랑_세션, 상품1_ID);

            // when
            var 응답 = 거래_제안_상태_조회_요청(말랑_세션, 상품1_ID);

            // then
            assertThat(응답.as(TradeSuggestionStatusResponse.class))
                    .usingRecursiveComparison()
                    .ignoringFields("suggestedDate")
                    .isEqualTo(new TradeSuggestionStatusResponse(true, LocalDateTime.now()));
        }

        @Test
        void 거래_제안을_하지_않은_경우() {
            // given
            회원가입_요청(말랑_회원가입_정보);
            회원가입_요청(동훈_회원가입_정보);
            var 말랑_세션 = 로그인_후_세션_추출("mallang1234", "mallang12345!@#");
            var 동훈_세션 = 로그인_후_세션_추출("donghun1234", "donghun12345!@#");
            var 상품1_ID = ID를_추출한다(상품_등록_요청(동훈_세션, 상품1_정보));

            // when
            var 응답 = 거래_제안_상태_조회_요청(말랑_세션, 상품1_ID);

            // then
            assertThat(응답.as(TradeSuggestionStatusResponse.class))
                    .usingRecursiveComparison()
                    .ignoringFields("suggestedDate")
                    .isEqualTo(new TradeSuggestionStatusResponse(false, null));
        }
    }
}

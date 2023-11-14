package com.oneandahalf.backend.acceptance.trade;

import static com.oneandahalf.backend.acceptance.AcceptanceSteps.ID를_추출한다;
import static com.oneandahalf.backend.acceptance.AcceptanceSteps.권한_없음;
import static com.oneandahalf.backend.acceptance.AcceptanceSteps.생성됨;
import static com.oneandahalf.backend.acceptance.AcceptanceSteps.예외_메세지를_검증한다;
import static com.oneandahalf.backend.acceptance.AcceptanceSteps.응답_상태를_검증한다;
import static com.oneandahalf.backend.acceptance.AcceptanceSteps.잘못된_요청;
import static com.oneandahalf.backend.acceptance.member.MemberAcceptanceSteps.로그인_후_세션_추출;
import static com.oneandahalf.backend.acceptance.member.MemberAcceptanceSteps.회원가입_요청;
import static com.oneandahalf.backend.acceptance.product.ProductAcceptanceSteps.상품_등록_요청;
import static com.oneandahalf.backend.acceptance.trade.TradeAcceptanceSteps.거래_확정_요청;
import static com.oneandahalf.backend.acceptance.trade.TradeSuggestionAcceptanceSteps.거래_제안_요청;
import static com.oneandahalf.backend.member.domain.ActivityArea.SEOUL;

import com.oneandahalf.backend.acceptance.AcceptanceTest;
import com.oneandahalf.backend.member.presentation.request.SignupRequest;
import com.oneandahalf.backend.product.presentation.request.RegisterProductRequest;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("거래 인수테스트")
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
public class TradeAcceptanceTest {

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

    private final SignupRequest 누군가_회원가입_정보 = SignupRequest.builder()
            .username("12341234132")
            .password("12341234132")
            .nickname("12341234132")
            .activityArea(SEOUL)
            .profileImageName("12341234132")
            .build();

    private final RegisterProductRequest 상품1_정보 = RegisterProductRequest.builder()
            .name("말랑이")
            .description("말랑말랑 말랑이")
            .price(10_000)
            .productImageNames(List.of("말랑이_사진1", "말랑이_사진2"))
            .build();

    @Nested
    class 거래_확정_API extends AcceptanceTest {

        @Test
        void 거래를_확정한다() {
            // given
            회원가입_요청(말랑_회원가입_정보);
            회원가입_요청(동훈_회원가입_정보);
            var 말랑_세션 = 로그인_후_세션_추출("mallang1234", "mallang12345!@#");
            var 동훈_세션 = 로그인_후_세션_추출("donghun1234", "donghun12345!@#");
            var 상품1_ID = ID를_추출한다(상품_등록_요청(동훈_세션, 상품1_정보));
            var 거래_제안_ID = ID를_추출한다(거래_제안_요청(말랑_세션, 상품1_ID));

            // when
            var 응답 = 거래_확정_요청(동훈_세션, 거래_제안_ID);

            // then
            응답_상태를_검증한다(응답, 생성됨);
        }

        @Test
        void 판매자가_아니라면_예외() {
            // given
            회원가입_요청(말랑_회원가입_정보);
            회원가입_요청(동훈_회원가입_정보);
            회원가입_요청(누군가_회원가입_정보);
            var 말랑_세션 = 로그인_후_세션_추출("mallang1234", "mallang12345!@#");
            var 동훈_세션 = 로그인_후_세션_추출("donghun1234", "donghun12345!@#");
            var 누군가_세션 = 로그인_후_세션_추출("12341234132", "12341234132");
            var 상품1_ID = ID를_추출한다(상품_등록_요청(동훈_세션, 상품1_정보));
            var 거래_제안_ID = ID를_추출한다(거래_제안_요청(말랑_세션, 상품1_ID));

            // when
            var 응답 = 거래_확정_요청(누군가_세션, 거래_제안_ID);

            // then
            응답_상태를_검증한다(응답, 권한_없음);
            예외_메세지를_검증한다(응답, "상품을 판매할 권한이 없습니다.");
        }

        @Test
        void 이미_판매된_상품이라면_예외() {
            // given
            회원가입_요청(말랑_회원가입_정보);
            회원가입_요청(동훈_회원가입_정보);
            회원가입_요청(누군가_회원가입_정보);
            var 말랑_세션 = 로그인_후_세션_추출("mallang1234", "mallang12345!@#");
            var 동훈_세션 = 로그인_후_세션_추출("donghun1234", "donghun12345!@#");
            var 누군가_세션 = 로그인_후_세션_추출("12341234132", "12341234132");
            var 상품1_ID = ID를_추출한다(상품_등록_요청(동훈_세션, 상품1_정보));
            var 말랑_거래_제안_ID = ID를_추출한다(거래_제안_요청(말랑_세션, 상품1_ID));
            var 누군가_거래_제안_ID = ID를_추출한다(거래_제안_요청(누군가_세션, 상품1_ID));
            거래_확정_요청(동훈_세션, 누군가_거래_제안_ID);

            // when
            var 응답 = 거래_확정_요청(동훈_세션, 말랑_거래_제안_ID);

            // then
            응답_상태를_검증한다(응답, 잘못된_요청);
            예외_메세지를_검증한다(응답, "이미 거래완료된 상품입니다.");
        }
    }
}

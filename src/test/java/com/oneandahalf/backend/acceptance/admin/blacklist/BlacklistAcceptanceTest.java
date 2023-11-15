package com.oneandahalf.backend.acceptance.admin.blacklist;

import static com.oneandahalf.backend.acceptance.AcceptanceSteps.ID를_추출한다;
import static com.oneandahalf.backend.acceptance.AcceptanceSteps.권한_없음;
import static com.oneandahalf.backend.acceptance.AcceptanceSteps.본문_없음;
import static com.oneandahalf.backend.acceptance.AcceptanceSteps.생성됨;
import static com.oneandahalf.backend.acceptance.AcceptanceSteps.응답_상태를_검증한다;
import static com.oneandahalf.backend.acceptance.AcceptanceSteps.인증되지_않음;
import static com.oneandahalf.backend.acceptance.admin.auth.AdminAuthAcceptanceSteps.어드민_로그인_요청;
import static com.oneandahalf.backend.acceptance.admin.blacklist.BlacklistAcceptanceSteps.블랙리스트_목록_조회_요청;
import static com.oneandahalf.backend.acceptance.admin.blacklist.BlacklistAcceptanceSteps.블랙리스트_제거_요청;
import static com.oneandahalf.backend.acceptance.admin.blacklist.BlacklistAcceptanceSteps.블랙리스트_추가_요청;
import static com.oneandahalf.backend.acceptance.admin.blacklist.BlacklistAcceptanceSteps.블랙리스트가_아닌_회원_목록_조회;
import static com.oneandahalf.backend.acceptance.member.MemberAcceptanceSteps.로그인_요청;
import static com.oneandahalf.backend.acceptance.member.MemberAcceptanceSteps.로그인_후_세션_추출;
import static com.oneandahalf.backend.acceptance.member.MemberAcceptanceSteps.회원가입_요청;
import static com.oneandahalf.backend.acceptance.product.ProductAcceptanceSteps.상품_검색_요청;
import static com.oneandahalf.backend.acceptance.product.ProductAcceptanceSteps.상품_등록_요청;
import static com.oneandahalf.backend.acceptance.trade.TradeSuggestionAcceptanceSteps.거래_제안_요청;
import static com.oneandahalf.backend.acceptance.trade.TradeSuggestionAcceptanceSteps.거래_제안_조회_요청;
import static com.oneandahalf.backend.member.domain.ActivityArea.SEOUL;
import static org.assertj.core.api.Assertions.assertThat;

import com.oneandahalf.backend.acceptance.AcceptanceTest;
import com.oneandahalf.backend.admin.auth.presentation.request.AdminLoginRequest;
import com.oneandahalf.backend.common.page.PageResponse;
import com.oneandahalf.backend.member.presentation.request.SignupRequest;
import com.oneandahalf.backend.member.query.response.BlacklistResponse;
import com.oneandahalf.backend.member.query.response.NotBlacklistMemberResponse;
import com.oneandahalf.backend.product.presentation.request.RegisterProductRequest;
import com.oneandahalf.backend.product.query.response.ProductSearchResponse;
import com.oneandahalf.backend.trade.query.response.TradeSuggestionResponse;
import io.restassured.common.mapper.TypeRef;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("블랙리스트 인수테스트")
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
public class BlacklistAcceptanceTest {

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

    private final RegisterProductRequest 상품1_요청 = RegisterProductRequest.builder()
            .name("말랑이")
            .description("말랑말랑 말랑이")
            .price(10_000)
            .productImageNames(List.of("말랑이_사진1", "말랑이_사진2"))
            .build();

    private final RegisterProductRequest 상품2_요청 = RegisterProductRequest.builder()
            .name("몰랑이")
            .description("몰랑몰랑 몰랑이")
            .price(20_000)
            .productImageNames(List.of("몰랑이_사진1"))
            .build();

    @Nested
    class 블랙리스트_추가_API extends AcceptanceTest {

        @Test
        void 회원을_블랙리스트에_추가한다() {
            // given
            AdminLoginRequest request = new AdminLoginRequest("admin", "admin");
            var 어드민_세션 = 어드민_로그인_요청(request).cookie("JSESSIONID");
            var 말랑_ID = ID를_추출한다(회원가입_요청(말랑_회원가입_정보));

            // when
            var 응답 = 블랙리스트_추가_요청(어드민_세션, 말랑_ID);

            // then
            응답_상태를_검증한다(응답, 생성됨);
        }

        @Test
        void 어드민이_아니라면_요청할_수_없다() {
            // given
            AdminLoginRequest request = new AdminLoginRequest("admin", "admin");
            var 어드민_세션 = 어드민_로그인_요청(request).cookie("JSESSIONID");
            var 말랑_ID = ID를_추출한다(회원가입_요청(말랑_회원가입_정보));
            var 말랑_세션 = 로그인_후_세션_추출("mallang1234", "mallang12345!@#");

            // when
            var 응답 = 블랙리스트_추가_요청(말랑_세션, 말랑_ID);

            // then
            응답_상태를_검증한다(응답, 인증되지_않음);
        }

        @Test
        void 블랙리스트에_추가된_회원의_게시글은_모두_삭제된다() {
            // given
            AdminLoginRequest request = new AdminLoginRequest("admin", "admin");
            var 어드민_세션 = 어드민_로그인_요청(request).cookie("JSESSIONID");
            var 말랑_ID = ID를_추출한다(회원가입_요청(말랑_회원가입_정보));
            var 말랑_세션 = 로그인_후_세션_추출("mallang1234", "mallang12345!@#");
            상품_등록_요청(말랑_세션, 상품1_요청);
            상품_등록_요청(말랑_세션, 상품2_요청);
            블랙리스트_추가_요청(어드민_세션, 말랑_ID);

            // when
            var 응답 = 상품_검색_요청(null, null, null, null);

            // then
            PageResponse<ProductSearchResponse> response = 응답.as(new TypeRef<>() {
            });
            assertThat(response.content()).isEmpty();
        }

        @Test
        void 블랙리스트에_추가되면_제아한_거래_요청이_모두_제거된다() {
            // given
            AdminLoginRequest request = new AdminLoginRequest("admin", "admin");
            var 어드민_세션 = 어드민_로그인_요청(request).cookie("JSESSIONID");
            var 말랑_ID = ID를_추출한다(회원가입_요청(말랑_회원가입_정보));
            회원가입_요청(말랑_회원가입_정보);
            회원가입_요청(동훈_회원가입_정보);
            var 말랑_세션 = 로그인_후_세션_추출("mallang1234", "mallang12345!@#");
            var 동훈_세션 = 로그인_후_세션_추출("donghun1234", "donghun12345!@#");
            var 상품1_ID = ID를_추출한다(상품_등록_요청(동훈_세션, 상품1_요청));
            거래_제안_요청(말랑_세션, 상품1_ID);

            블랙리스트_추가_요청(어드민_세션, 말랑_ID);

            // when
            var 응답 = 거래_제안_조회_요청(동훈_세션, 상품1_ID);

            // then
            List<TradeSuggestionResponse> responses = 응답.as(new TypeRef<>() {
            });
            assertThat(responses).isEmpty();
        }
    }

    @Nested
    class 블랙리스트_제거_API extends AcceptanceTest {

        @Test
        void 회원을_블랙리스트에서_제거한다() {
            // given
            AdminLoginRequest request = new AdminLoginRequest("admin", "admin");
            var 어드민_세션 = 어드민_로그인_요청(request).cookie("JSESSIONID");
            var 말랑_ID = ID를_추출한다(회원가입_요청(말랑_회원가입_정보));
            블랙리스트_추가_요청(어드민_세션, 말랑_ID);

            // when
            var 응답 = 블랙리스트_제거_요청(어드민_세션, 말랑_ID);

            // then
            응답_상태를_검증한다(응답, 본문_없음);
        }

        @Test
        void 어드민이_아니라면_요청할_수_없다() {
            // given
            AdminLoginRequest request = new AdminLoginRequest("admin", "admin");
            var 어드민_세션 = 어드민_로그인_요청(request).cookie("JSESSIONID");
            var 말랑_ID = ID를_추출한다(회원가입_요청(말랑_회원가입_정보));
            var 말랑_세션 = 로그인_후_세션_추출("mallang1234", "mallang12345!@#");

            블랙리스트_추가_요청(어드민_세션, 말랑_ID);

            // when
            var 응답 = 블랙리스트_제거_요청(말랑_세션, 말랑_ID);

            // then
            응답_상태를_검증한다(응답, 인증되지_않음);
        }
    }

    @Nested
    class 블랙리스트_목록_조회_API extends AcceptanceTest {

        @Test
        void 블랙리스트_목록_조회() {
            // given
            AdminLoginRequest request = new AdminLoginRequest("admin", "admin");
            var 어드민_세션 = 어드민_로그인_요청(request).cookie("JSESSIONID");
            var 말랑_ID = ID를_추출한다(회원가입_요청(말랑_회원가입_정보));
            var 동훈_ID = ID를_추출한다(회원가입_요청(동훈_회원가입_정보));
            블랙리스트_추가_요청(어드민_세션, 말랑_ID);
            블랙리스트_추가_요청(어드민_세션, 동훈_ID);

            // when
            var 응답 = 블랙리스트_목록_조회_요청(어드민_세션);

            // then
            List<BlacklistResponse> responses = 응답.as(new TypeRef<>() {
            });
            assertThat(responses).hasSize(2);
        }

        @Test
        void 어드민만_가능하다() {
            // given
            AdminLoginRequest request = new AdminLoginRequest("admin", "admin");
            var 어드민_세션 = 어드민_로그인_요청(request).cookie("JSESSIONID");
            var 말랑_ID = ID를_추출한다(회원가입_요청(말랑_회원가입_정보));
            var 말랑_세션 = 로그인_후_세션_추출("mallang1234", "mallang12345!@#");

            // when
            var 응답 = 블랙리스트_목록_조회_요청(말랑_세션);

            // then
            응답_상태를_검증한다(응답, 인증되지_않음);
        }
    }

    @Nested
    class 블랙리스트의_점근_불가_테스트 extends AcceptanceTest {

        @Test
        void 블랙리스트는_상품_등록이_불가능하다() {
            // given
            AdminLoginRequest request = new AdminLoginRequest("admin", "admin");
            var 어드민_세션 = 어드민_로그인_요청(request).cookie("JSESSIONID");
            var 말랑_ID = ID를_추출한다(회원가입_요청(말랑_회원가입_정보));
            var 말랑_세션 = 로그인_후_세션_추출("mallang1234", "mallang12345!@#");
            블랙리스트_추가_요청(어드민_세션, 말랑_ID);

            // when
            var 응답 = 상품_등록_요청(말랑_세션, 상품1_요청);

            // then
            응답_상태를_검증한다(응답, 권한_없음);
        }

        @Test
        void 블랙리스트는_로그인이_불가능하다() {
            // given
            AdminLoginRequest request = new AdminLoginRequest("admin", "admin");
            var 어드민_세션 = 어드민_로그인_요청(request).cookie("JSESSIONID");
            var 말랑_ID = ID를_추출한다(회원가입_요청(말랑_회원가입_정보));
            블랙리스트_추가_요청(어드민_세션, 말랑_ID);

            // when
            var 응답 = 로그인_요청("mallang1234", "mallang12345!@#");

            // then
            응답_상태를_검증한다(응답, 권한_없음);
        }
    }

    @Nested
    class 블랙리스트가_아닌_회원_목록_조회_API extends AcceptanceTest {

        @Test
        void 블랙리스트가_아닌_회원_목록을_조회한다() {
            // given
            AdminLoginRequest request = new AdminLoginRequest("admin", "admin");
            var 어드민_세션 = 어드민_로그인_요청(request).cookie("JSESSIONID");
            var 말랑_ID = ID를_추출한다(회원가입_요청(말랑_회원가입_정보));
            회원가입_요청(동훈_회원가입_정보);
            블랙리스트_추가_요청(어드민_세션, 말랑_ID);

            // when
            var 응답 = 블랙리스트가_아닌_회원_목록_조회(어드민_세션);

            // then
            List<NotBlacklistMemberResponse> responses = 응답.as(new TypeRef<>() {
            });
            assertThat(responses)
                    .extracting(NotBlacklistMemberResponse::nickname)
                    .containsExactly("donghun");
        }
    }
}

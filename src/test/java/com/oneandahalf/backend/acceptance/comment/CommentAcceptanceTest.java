package com.oneandahalf.backend.acceptance.comment;

import static com.oneandahalf.backend.acceptance.AcceptanceSteps.ID를_추출한다;
import static com.oneandahalf.backend.acceptance.AcceptanceSteps.생성됨;
import static com.oneandahalf.backend.acceptance.AcceptanceSteps.응답_상태를_검증한다;
import static com.oneandahalf.backend.acceptance.comment.CommentAcceptanceSteps.댓글_작성_요청;
import static com.oneandahalf.backend.acceptance.comment.CommentAcceptanceSteps.댓글_조회_요청;
import static com.oneandahalf.backend.acceptance.member.MemberAcceptanceSteps.로그인_후_세션_추출;
import static com.oneandahalf.backend.acceptance.member.MemberAcceptanceSteps.회원가입_요청;
import static com.oneandahalf.backend.acceptance.product.ProductAcceptanceSteps.상품_등록_요청;
import static com.oneandahalf.backend.member.domain.ActivityArea.SEOUL;
import static org.assertj.core.api.Assertions.assertThat;

import com.oneandahalf.backend.acceptance.AcceptanceTest;
import com.oneandahalf.backend.comment.query.response.CommentByProductResponse;
import com.oneandahalf.backend.member.presentation.request.SignupRequest;
import com.oneandahalf.backend.product.presentation.request.RegisterProductRequest;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("댓글 인수테스트")
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
public class CommentAcceptanceTest {

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
    class 댓글_작성_API extends AcceptanceTest {

        @Test
        void 댓글을_작성한다() {
            // given
            회원가입_요청(말랑_회원가입_정보);
            var 말랑_세션 = 로그인_후_세션_추출("mallang1234", "mallang12345!@#");
            var 상품1_ID = ID를_추출한다(상품_등록_요청(말랑_세션, 상품1_정보));

            // when
            var 응답 = 댓글_작성_요청(말랑_세션, 상품1_ID, "이건 저의 상품입니다.");

            // then
            응답_상태를_검증한다(응답, 생성됨);
        }
    }

    @Nested
    class 상품에_달린_댓글_조회_API extends AcceptanceTest {

        @Test
        void 댓글들을_조회한다() {
            // given
            회원가입_요청(말랑_회원가입_정보);
            회원가입_요청(동훈_회원가입_정보);
            var 말랑_세션 = 로그인_후_세션_추출("mallang1234", "mallang12345!@#");
            var 동훈_세션 = 로그인_후_세션_추출("donghun1234", "donghun12345!@#");
            var 상품1_ID = ID를_추출한다(상품_등록_요청(말랑_세션, 상품1_정보));

            댓글_작성_요청(말랑_세션, 상품1_ID, "이건 저의 상품입니다.");
            댓글_작성_요청(동훈_세션, 상품1_ID, "어쩔");
            댓글_작성_요청(말랑_세션, 상품1_ID, "응");

            // when
            ExtractableResponse<Response> 응답 = 댓글_조회_요청(상품1_ID);

            // then
            List<CommentByProductResponse> responses = 응답.as(new TypeRef<>() {
            });
            assertThat(responses)
                    .extracting(CommentByProductResponse::content)
                    .containsExactly("이건 저의 상품입니다.", "어쩔", "응");
        }
    }
}

package com.oneandahalf.backend.acceptance.keyword;

import static com.oneandahalf.backend.acceptance.AcceptanceSteps.ID를_추출한다;
import static com.oneandahalf.backend.acceptance.AcceptanceSteps.본문_없음;
import static com.oneandahalf.backend.acceptance.AcceptanceSteps.생성됨;
import static com.oneandahalf.backend.acceptance.AcceptanceSteps.예외_메세지를_검증한다;
import static com.oneandahalf.backend.acceptance.AcceptanceSteps.응답_상태를_검증한다;
import static com.oneandahalf.backend.acceptance.AcceptanceSteps.중복됨;
import static com.oneandahalf.backend.acceptance.keyword.KeywordAcceptanceSteps.키워드_삭제_요청;
import static com.oneandahalf.backend.acceptance.keyword.KeywordAcceptanceSteps.키워드_작성_요청;
import static com.oneandahalf.backend.acceptance.member.MemberAcceptanceSteps.로그인_후_세션_추출;
import static com.oneandahalf.backend.acceptance.member.MemberAcceptanceSteps.회원가입_요청;
import static com.oneandahalf.backend.member.domain.ActivityArea.SEOUL;

import com.oneandahalf.backend.acceptance.AcceptanceTest;
import com.oneandahalf.backend.member.presentation.request.SignupRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("키워드 인수테스트")
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
public class KeywordAcceptanceTest {

    private final SignupRequest 말랑_회원가입_정보 = SignupRequest.builder()
            .username("mallang1234")
            .password("mallang12345!@#")
            .nickname("mallang")
            .activityArea(SEOUL)
            .profileImageName("mallangImage")
            .build();

    @Nested
    class 키워드_작성_API extends AcceptanceTest {

        @Test
        void 키워드를_작성한다() {
            // given
            회원가입_요청(말랑_회원가입_정보);
            var 말랑_세션 = 로그인_후_세션_추출("mallang1234", "mallang12345!@#");

            // when
            var 응답 = 키워드_작성_요청(말랑_세션, "키워드 1");

            // then
            응답_상태를_검증한다(응답, 생성됨);
        }

        @Test
        void 중복된_키워드가_있으면_예외() {
            // given
            회원가입_요청(말랑_회원가입_정보);
            var 말랑_세션 = 로그인_후_세션_추출("mallang1234", "mallang12345!@#");
            키워드_작성_요청(말랑_세션, "키워드 1");

            // when
            var 응답 = 키워드_작성_요청(말랑_세션, "키워드 1");

            // then
            응답_상태를_검증한다(응답, 중복됨);
            예외_메세지를_검증한다(응답, "중복된 키워드가 존재합니다.");
        }
    }

    @Nested
    class 키워드_삭제_API extends AcceptanceTest {

        @Test
        void 키워드를_제거한다() {
            // given
            회원가입_요청(말랑_회원가입_정보);
            var 말랑_세션 = 로그인_후_세션_추출("mallang1234", "mallang12345!@#");
            var 키워드_ID = ID를_추출한다(키워드_작성_요청(말랑_세션, "키워드 1"));

            // when
            var 응답 = 키워드_삭제_요청(말랑_세션, 키워드_ID);

            // then
            응답_상태를_검증한다(응답, 본문_없음);
        }
    }
}

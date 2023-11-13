package com.oneandahalf.backend.acceptance.member;

import static com.oneandahalf.backend.acceptance.AcceptanceSteps.ID를_추출한다;
import static com.oneandahalf.backend.acceptance.AcceptanceSteps.값이_존재한다;
import static com.oneandahalf.backend.acceptance.AcceptanceSteps.생성됨;
import static com.oneandahalf.backend.acceptance.AcceptanceSteps.예외_메세지를_검증한다;
import static com.oneandahalf.backend.acceptance.AcceptanceSteps.응답_상태를_검증한다;
import static com.oneandahalf.backend.acceptance.AcceptanceSteps.인증되지_않음;
import static com.oneandahalf.backend.acceptance.AcceptanceSteps.잘못된_요청;
import static com.oneandahalf.backend.acceptance.AcceptanceSteps.중복됨;
import static com.oneandahalf.backend.acceptance.member.MemberAcceptanceSteps.로그인_요청;
import static com.oneandahalf.backend.acceptance.member.MemberAcceptanceSteps.회원가입_요청;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.web.server.Cookie.SameSite.NONE;

import com.oneandahalf.backend.acceptance.AcceptanceTest;
import com.oneandahalf.backend.member.domain.ActivityArea;
import com.oneandahalf.backend.member.presentation.request.SignupRequest;
import io.restassured.http.Cookie;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("회원 인수테스트")
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
public class MemberAcceptanceTest {

    @Nested
    class 회원가입_API extends AcceptanceTest {

        @Test
        void 회원가입을_진핸한다() {
            // given
            SignupRequest request = SignupRequest.builder()
                    .username("mallang1234")
                    .password("mallang12345!@#")
                    .activityArea(ActivityArea.SEOUL)
                    .nickname("mallang")
                    .profileImageName("mallangImage")
                    .build();

            // when
            var 응답 = 회원가입_요청(request);

            // then
            응답_상태를_검증한다(응답, 생성됨);
            값이_존재한다(ID를_추출한다(응답));
        }

        @Test
        void 중복되는_아이디가_있다면_예외() {
            // given
            SignupRequest request = SignupRequest.builder()
                    .username("mallang1234")
                    .password("mallang12345!@#")
                    .activityArea(ActivityArea.SEOUL)
                    .nickname("mallang")
                    .profileImageName("mallangImage")
                    .build();
            회원가입_요청(request);

            // when
            var 응답 = 회원가입_요청(request);

            // then
            응답_상태를_검증한다(응답, 중복됨);
            예외_메세지를_검증한다(응답, "중복되는 아이디입니다. 다른 아이디로 가입해주세요.");
        }

        @Test
        void 아이디가_8글자_이하라면_예외() {
            // given
            SignupRequest request = SignupRequest.builder()
                    .username("1234567")
                    .password("mallang12345!@#")
                    .activityArea(ActivityArea.SEOUL)
                    .nickname("mallang")
                    .profileImageName("mallangImage")
                    .build();

            // when
            var 응답 = 회원가입_요청(request);

            // then
            응답_상태를_검증한다(응답, 잘못된_요청);
            예외_메세지를_검증한다(응답, "아이디는 8글자 이상이어야 합니다.");
        }

        @Test
        void 비밀번호가_8글자_이하라면_예외() {
            // given
            SignupRequest request = SignupRequest.builder()
                    .username("mallang1234")
                    .password("1234567")
                    .activityArea(ActivityArea.SEOUL)
                    .nickname("mallang")
                    .profileImageName("mallangImage")
                    .build();

            // when
            var 응답 = 회원가입_요청(request);

            // then
            응답_상태를_검증한다(응답, 잘못된_요청);
            예외_메세지를_검증한다(응답, "비밀번호는 8글자 이상이어야 합니다.");
        }
    }

    @Nested
    class 로그인_API extends AcceptanceTest {

        @Test
        void 로그인에_성공하면_세션을_발급한다() {
            // given
            SignupRequest request = SignupRequest.builder()
                    .username("mallang1234")
                    .password("mallang12345!@#")
                    .activityArea(ActivityArea.SEOUL)
                    .nickname("mallang")
                    .profileImageName("mallangImage")
                    .build();
            회원가입_요청(request);

            // when
            var 응답 = 로그인_요청("mallang1234", "mallang12345!@#");

            // then
            Cookie cookie = 응답.detailedCookie("JSESSIONID");
            assertThat(cookie.getSameSite()).isEqualTo("None");
            assertThat(cookie.isHttpOnly()).isTrue();
            assertThat(cookie.isSecured()).isTrue();
        }

        @Test
        void 비밀번호가_다르면_로그인_실패() {
            // given
            SignupRequest request = SignupRequest.builder()
                    .username("mallang1234")
                    .password("mallang12345!@#")
                    .activityArea(ActivityArea.SEOUL)
                    .nickname("mallang")
                    .profileImageName("mallangImage")
                    .build();
            회원가입_요청(request);

            // when
            var 응답 = 로그인_요청("mallang1234", "mallang1234");

            // then
            응답_상태를_검증한다(응답, 인증되지_않음);
        }
    }
}

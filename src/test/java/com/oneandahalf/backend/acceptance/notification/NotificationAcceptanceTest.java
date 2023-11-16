package com.oneandahalf.backend.acceptance.notification;

import static com.oneandahalf.backend.acceptance.AcceptanceSteps.ID를_추출한다;
import static com.oneandahalf.backend.acceptance.AcceptanceSteps.multipartFile;
import static com.oneandahalf.backend.acceptance.keyword.KeywordAcceptanceSteps.키워드_작성_요청;
import static com.oneandahalf.backend.acceptance.member.MemberAcceptanceSteps.로그인_후_세션_추출;
import static com.oneandahalf.backend.acceptance.member.MemberAcceptanceSteps.회원가입_요청;
import static com.oneandahalf.backend.acceptance.notification.NotificationAcceptanceSteps.내_알림_목록_조회_요청;
import static com.oneandahalf.backend.acceptance.notification.NotificationAcceptanceSteps.알림_읽음_요청;
import static com.oneandahalf.backend.acceptance.product.ProductAcceptanceSteps.상품_등록_요청;
import static com.oneandahalf.backend.member.domain.ActivityArea.SEOUL;
import static org.assertj.core.api.Assertions.assertThat;

import com.oneandahalf.backend.acceptance.AcceptanceTest;
import com.oneandahalf.backend.member.presentation.request.SignupRequest;
import com.oneandahalf.backend.notification.query.response.MyNotificationsResponse;
import com.oneandahalf.backend.notification.query.response.MyNotificationsResponse.NotificationResponse;
import com.oneandahalf.backend.product.presentation.request.RegisterProductRequest;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("알림 인수테스트")
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
public class NotificationAcceptanceTest {

    private final SignupRequest 말랑_회원가입_정보 = SignupRequest.builder()
            .username("mallang1234")
            .password("mallang12345!@#")
            .nickname("mallang")
            .activityArea(SEOUL)
            .profileImage(multipartFile("mallangImage"))
            .build();

    private final SignupRequest 동훈_회원가입_정보 = SignupRequest.builder()
            .username("donghun1234")
            .password("donghun12345!@#")
            .nickname("donghun")
            .activityArea(SEOUL)
            .profileImage(multipartFile("donghunImage"))
            .build();

    @Nested
    class 내_알림_목록_조회_API extends AcceptanceTest {

        @Test
        void 내_알림_목록을_조회한다() {
            // given
            회원가입_요청(말랑_회원가입_정보);
            회원가입_요청(동훈_회원가입_정보);
            var 말랑_세션 = 로그인_후_세션_추출("mallang1234", "mallang12345!@#");
            var 동훈_세션 = 로그인_후_세션_추출("donghun1234", "donghun12345!@#");
            키워드_작성_요청(말랑_세션, "사과");
            키워드_작성_요청(말랑_세션, "배");
            var 상품_ID = ID를_추출한다(상품_등록_요청(동훈_세션, RegisterProductRequest.builder()
                    .name("맛있는 사과입니다.")
                    .description("하하")
                    .price(1000)
                    .productImages(List.of(multipartFile("사진")))
                    .build()));

            // when
            var 응답 = 내_알림_목록_조회_요청(말랑_세션);

            // then
            assertThat(응답.as(MyNotificationsResponse.class))
                    .usingRecursiveComparison()
                    .ignoringFields("notifications.id", "notifications.createdDate")
                    .isEqualTo(new MyNotificationsResponse(
                            List.of(NotificationResponse.builder()
                                    .content("\"사과\" 키워드 관련 게시물이 올라왔어요.")
                                    .read(false)
                                    .linkedURI("/products/" + 상품_ID)
                                    .build()),
                            true
                    ));
        }
    }

    @Nested
    class 알림_읽음_처리_API extends AcceptanceTest {

        @Test
        void 알림_읽음_처리() {
            // given
            회원가입_요청(말랑_회원가입_정보);
            회원가입_요청(동훈_회원가입_정보);
            var 말랑_세션 = 로그인_후_세션_추출("mallang1234", "mallang12345!@#");
            var 동훈_세션 = 로그인_후_세션_추출("donghun1234", "donghun12345!@#");
            키워드_작성_요청(말랑_세션, "사과");
            키워드_작성_요청(말랑_세션, "배");
            var 상품_ID = ID를_추출한다(상품_등록_요청(동훈_세션, RegisterProductRequest.builder()
                    .name("맛있는 사과입니다.")
                    .description("하하")
                    .price(1000)
                    .productImages(List.of(multipartFile("사진")))
                    .build()));
            var 내_알림_조회_응답 = 내_알림_목록_조회_요청(말랑_세션);
            var 알림_ID = 내_알림_조회_응답.as(MyNotificationsResponse.class)
                    .notifications()
                    .get(0)
                    .id();

            // when
            알림_읽음_요청(말랑_세션, 알림_ID);

            // then
            var 응답 = 내_알림_목록_조회_요청(말랑_세션);
            assertThat(응답.as(MyNotificationsResponse.class))
                    .usingRecursiveComparison()
                    .ignoringFields("notifications.id", "notifications.createdDate")
                    .isEqualTo(new MyNotificationsResponse(
                            List.of(NotificationResponse.builder()
                                    .content("\"사과\" 키워드 관련 게시물이 올라왔어요.")
                                    .read(true)
                                    .linkedURI("/products/" + 상품_ID)
                                    .build()),
                            false
                    ));
        }
    }
}

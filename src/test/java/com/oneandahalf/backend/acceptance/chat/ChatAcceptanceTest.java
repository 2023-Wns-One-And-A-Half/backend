package com.oneandahalf.backend.acceptance.chat;

import static com.oneandahalf.backend.acceptance.AcceptanceSteps.ID를_추출한다;
import static com.oneandahalf.backend.acceptance.AcceptanceSteps.생성됨;
import static com.oneandahalf.backend.acceptance.AcceptanceSteps.응답_상태를_검증한다;
import static com.oneandahalf.backend.acceptance.chat.ChatAcceptanceSteps.메세지_전송_요청;
import static com.oneandahalf.backend.acceptance.chat.ChatAcceptanceSteps.채팅방_불러오기_요청;
import static com.oneandahalf.backend.acceptance.chat.ChatAcceptanceSteps.특정_메세지_이후_새로운_메세지가_있으면_받아온다;
import static com.oneandahalf.backend.acceptance.member.MemberAcceptanceSteps.로그인_후_세션_추출;
import static com.oneandahalf.backend.acceptance.member.MemberAcceptanceSteps.회원가입_요청;
import static com.oneandahalf.backend.acceptance.product.ProductAcceptanceSteps.상품_등록_요청;
import static com.oneandahalf.backend.member.domain.ActivityArea.SEOUL;
import static org.assertj.core.api.Assertions.assertThat;

import com.oneandahalf.backend.acceptance.AcceptanceTest;
import com.oneandahalf.backend.chat.query.response.ChatMessageResponse;
import com.oneandahalf.backend.chat.query.response.ChatRoomResponse;
import com.oneandahalf.backend.member.presentation.request.SignupRequest;
import com.oneandahalf.backend.product.presentation.request.RegisterProductRequest;
import io.restassured.common.mapper.TypeRef;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("채팅 인수테스트")
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
public class ChatAcceptanceTest {

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
    class 채팅방_불러오기_API extends AcceptanceTest {

        @Test
        void 채팅방이_없다면_새로_만들고_불러온다() {
            // given
            var 말랑_ID = ID를_추출한다(회원가입_요청(말랑_회원가입_정보));
            var 동훈_ID = ID를_추출한다(회원가입_요청(동훈_회원가입_정보));
            var 말랑_세션 = 로그인_후_세션_추출("mallang1234", "mallang12345!@#");
            var 동훈_세션 = 로그인_후_세션_추출("donghun1234", "donghun12345!@#");
            var 상품1_ID = ID를_추출한다(상품_등록_요청(동훈_세션, 상품1_정보));

            // when
            var 응답 = 채팅방_불러오기_요청(말랑_세션, 상품1_ID, 말랑_ID);

            // then
            assertThat(응답.as(ChatRoomResponse.class))
                    .usingRecursiveComparison()
                    .ignoringExpectedNullFields()
                    .isEqualTo(ChatRoomResponse.builder()
                            .name("[말랑이] 에 대한 채팅")
                            .chatMessages(Collections.emptyList())
                            .build());
        }

        @Test
        void 채팅방이_존재하면_기존_채팅_내역을_모두_불러온다() {
            // given
            var 말랑_ID = ID를_추출한다(회원가입_요청(말랑_회원가입_정보));
            var 동훈_ID = ID를_추출한다(회원가입_요청(동훈_회원가입_정보));
            var 말랑_세션 = 로그인_후_세션_추출("mallang1234", "mallang12345!@#");
            var 동훈_세션 = 로그인_후_세션_추출("donghun1234", "donghun12345!@#");
            var 상품1_ID = ID를_추출한다(상품_등록_요청(동훈_세션, 상품1_정보));
            var 채팅방_ID = 채팅방_불러오기_요청(말랑_세션, 상품1_ID, 말랑_ID).as(ChatRoomResponse.class).id();

            메세지_전송_요청(말랑_세션, 동훈_ID, 채팅방_ID, "안녕하세요 판매자님!");
            메세지_전송_요청(동훈_세션, 말랑_ID, 채팅방_ID, "네 안녕하세요 말랑 고객님!");

            // when
            var 채팅방 = 채팅방_불러오기_요청(말랑_세션, 상품1_ID, 말랑_ID).as(ChatRoomResponse.class);

            // then
            assertThat(채팅방)
                    .usingRecursiveComparison()
                    .ignoringExpectedNullFields()
                    .isEqualTo(ChatRoomResponse.builder()
                            .name("[말랑이] 에 대한 채팅")
                            .chatMessages(List.of(
                                    ChatMessageResponse.builder()
                                            .content("안녕하세요 판매자님!")
                                            .read(false)
                                            .build(),
                                    ChatMessageResponse.builder()
                                            .content("네 안녕하세요 말랑 고객님!")
                                            .read(true)
                                            .build()
                            ))
                            .build());
        }
    }

    @Nested
    class 메세지_전송_API extends AcceptanceTest {

        @Test
        void 메세지를_전송한다() {
            // given
            var 말랑_ID = ID를_추출한다(회원가입_요청(말랑_회원가입_정보));
            var 동훈_ID = ID를_추출한다(회원가입_요청(동훈_회원가입_정보));
            var 말랑_세션 = 로그인_후_세션_추출("mallang1234", "mallang12345!@#");
            var 동훈_세션 = 로그인_후_세션_추출("donghun1234", "donghun12345!@#");
            var 상품1_ID = ID를_추출한다(상품_등록_요청(동훈_세션, 상품1_정보));
            var 채팅방_ID = 채팅방_불러오기_요청(말랑_세션, 상품1_ID, 말랑_ID).as(ChatRoomResponse.class).id();

            // when
            var 응답 = 메세지_전송_요청(말랑_세션, 동훈_ID, 채팅방_ID, "안녕하세요 판매자님!");

            // then
            응답_상태를_검증한다(응답, 생성됨);
        }
    }

    @Nested
    class 특정_메세지_이후_메세지_조회_API extends AcceptanceTest {

        @Test
        void 특정_메세지_이후_새로_전송된_메세지가_있으면_조회한다() {
            // given
            var 말랑_ID = ID를_추출한다(회원가입_요청(말랑_회원가입_정보));
            var 동훈_ID = ID를_추출한다(회원가입_요청(동훈_회원가입_정보));
            var 말랑_세션 = 로그인_후_세션_추출("mallang1234", "mallang12345!@#");
            var 동훈_세션 = 로그인_후_세션_추출("donghun1234", "donghun12345!@#");
            var 상품1_ID = ID를_추출한다(상품_등록_요청(동훈_세션, 상품1_정보));
            var 채팅방_ID = 채팅방_불러오기_요청(말랑_세션, 상품1_ID, 말랑_ID).as(ChatRoomResponse.class).id();

            메세지_전송_요청(말랑_세션, 동훈_ID, 채팅방_ID, "안녕하세요 판매자님!");
            메세지_전송_요청(동훈_세션, 말랑_ID, 채팅방_ID, "네 안녕하세요 말랑 고객님!");
            메세지_전송_요청(동훈_세션, 말랑_ID, 채팅방_ID, "고객님???");
            ChatRoomResponse chatRoomResponse = 채팅방_불러오기_요청(말랑_세션, 상품1_ID, 말랑_ID).as(ChatRoomResponse.class);
            ChatMessageResponse lastMessage = chatRoomResponse.chatMessages()
                    .get(chatRoomResponse.chatMessages().size() - 1);

            메세지_전송_요청(말랑_세션, 동훈_ID, 채팅방_ID, "네????");
            메세지_전송_요청(동훈_세션, 말랑_ID, 채팅방_ID, "안사실건가요??");

            // when
            var 응답 = 특정_메세지_이후_새로운_메세지가_있으면_받아온다(말랑_세션, lastMessage.id(), 채팅방_ID);

            // then
            List<ChatMessageResponse> responses = 응답.as(new TypeRef<>() {
            });
            assertThat(responses)
                    .extracting(ChatMessageResponse::content)
                    .containsExactly("네????", "안사실건가요??");
        }
    }
}

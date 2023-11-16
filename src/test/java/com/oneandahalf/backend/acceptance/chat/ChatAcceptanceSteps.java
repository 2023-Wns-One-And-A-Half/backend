package com.oneandahalf.backend.acceptance.chat;

import static com.oneandahalf.backend.acceptance.AcceptanceSteps.given;

import com.oneandahalf.backend.chat.presentation.request.SendMessageRequest;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

@SuppressWarnings("NonAsciiCharacters")
public class ChatAcceptanceSteps {

    public static ExtractableResponse<Response> 채팅방_불러오기_요청(String 세션, Long 상품_ID, Long 고객_ID) {
        return given(세션)
                .queryParam("productId", 상품_ID)
                .queryParam("clientId", 고객_ID)
                .get("/chats")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 메세지_전송_요청(
            String 세션,
            Long 수신자_ID,
            Long 채팅방_ID,
            String 메세지
    ) {
        return given(세션)
                .body(new SendMessageRequest(수신자_ID, 메세지))
                .post("/chats/{roomId}/messages", 채팅방_ID)
                .then()
                .log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 특정_메세지_이후_새로운_메세지가_있으면_받아온다(
            String 세션,
            Long 마지막_메세지_ID,
            Long 채팅방_ID
    ) {
        return given(세션)
                .queryParam("lastMessageId", 마지막_메세지_ID)
                .get("/chats/{roomId}", 채팅방_ID)
                .then()
                .log().all()
                .extract();
    }
}


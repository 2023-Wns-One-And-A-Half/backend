package com.oneandahalf.backend.chat.presentation;

import static org.springframework.http.HttpStatus.CREATED;

import com.oneandahalf.backend.chat.application.ChatService;
import com.oneandahalf.backend.chat.application.command.CreateChatRoomCommand;
import com.oneandahalf.backend.chat.presentation.request.SendMessageRequest;
import com.oneandahalf.backend.chat.query.ChatQueryService;
import com.oneandahalf.backend.chat.query.response.ChatMessageResponse;
import com.oneandahalf.backend.chat.query.response.ChatRoomResponse;
import com.oneandahalf.backend.member.presentation.support.Auth;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/chats")
@RestController
public class ChatController {

    private final ChatService chatService;
    private final ChatQueryService chatQueryService;

    @GetMapping
    public ResponseEntity<ChatRoomResponse> findRoom(
            @Auth Long memberId,
            @RequestParam("productId") Long productId,
            @RequestParam("clientId") Long clientId
    ) {
        Long roomId = chatService.createRoomIfNotExist(
                CreateChatRoomCommand.builder()
                        .chatRequesterId(memberId)
                        .productId(productId)
                        .clientId(clientId)
                        .build());
        return ResponseEntity.ok(chatQueryService.findChatRoom(memberId, roomId));
    }

    @PostMapping("/{roomId}/messages")
    public ResponseEntity<Void> sendMessage(
            @Auth Long memberId,
            @PathVariable("roomId") Long chatRoomId,
            @RequestBody SendMessageRequest request
    ) {
        chatService.sendMessage(request.toCommand(chatRoomId, memberId));
        return ResponseEntity.status(CREATED).build();
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<List<ChatMessageResponse>> findNewMessages(
            @Auth Long memberId,
            @PathVariable("roomId") Long chatRoomId,
            @RequestParam("lastMessageId") Long lastMessageId
    ) {
        return ResponseEntity.ok(chatQueryService.findNewMessages(memberId, chatRoomId, lastMessageId));
    }
}

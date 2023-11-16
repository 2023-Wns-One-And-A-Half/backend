package com.oneandahalf.backend.chat.application;

import com.oneandahalf.backend.chat.application.command.CreateChatRoomCommand;
import com.oneandahalf.backend.chat.application.command.SendMessageCommand;
import com.oneandahalf.backend.chat.domain.ChatMessage;
import com.oneandahalf.backend.chat.domain.ChatMessageRepository;
import com.oneandahalf.backend.chat.domain.ChatRoomRepository;
import com.oneandahalf.backend.chat.domain.ProductChatRoom;
import com.oneandahalf.backend.member.domain.Member;
import com.oneandahalf.backend.member.domain.MemberRepository;
import com.oneandahalf.backend.product.domain.Product;
import com.oneandahalf.backend.product.domain.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class ChatService {

    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;

    public Long createRoomIfNotExist(CreateChatRoomCommand command) {
        return chatRoomRepository.findByProductIdAndClientId(command.productId(), command.clientId())
                .orElseGet(() -> {
                    Product product = productRepository.getById(command.productId());
                    Member member = memberRepository.getById(command.clientId());
                    ProductChatRoom productChatRoom = new ProductChatRoom(product, member);
                    productChatRoom.open(command.chatRequesterId());
                    return chatRoomRepository.save(productChatRoom);
                }).getId();
    }

    public Long sendMessage(SendMessageCommand command) {
        ProductChatRoom room = chatRoomRepository.getById(command.chatRoomId());
        Member sender = memberRepository.getById(command.senderId());
        Member receiver = memberRepository.getById(command.receiverId());
        return chatMessageRepository.save(new ChatMessage(command.content(), room, sender, receiver))
                .getId();
    }
}

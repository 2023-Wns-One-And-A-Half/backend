package com.oneandahalf.backend.chat.domain;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import com.oneandahalf.backend.common.domain.CommonDomainModel;
import com.oneandahalf.backend.common.exception.ApplicationException;
import com.oneandahalf.backend.common.exception.ErrorCode;
import com.oneandahalf.backend.member.domain.Member;
import com.oneandahalf.backend.product.domain.Product;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ProductChatRoom extends CommonDomainModel {

    private Long productId;
    private String roomName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private Member seller;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Member client;

    public ProductChatRoom(Product product, Member client) {
        this.productId = product.getId();
        this.roomName = "[%s] 에 대한 채팅".formatted(product.getName());
        this.seller = product.getSeller();
        this.client = client;
    }

    public void open(Long memberId) {
        if (isParticipant(memberId)) {
            return;
        }
        throw new ApplicationException(new ErrorCode(BAD_REQUEST, "잘못된 채팅 생성 요청입니다."));
    }

    public boolean isParticipant(Long memberId) {
        return isClient(memberId) || isSeller(memberId);
    }

    private boolean isClient(Long memberId) {
        return client.getId().equals(memberId);
    }

    private boolean isSeller(Long memberId) {
        return seller.getId().equals(memberId);
    }
}

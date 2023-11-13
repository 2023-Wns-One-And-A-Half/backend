package com.oneandahalf.backend.product.application.command;

import com.oneandahalf.backend.member.domain.Member;
import com.oneandahalf.backend.product.domain.Product;
import java.util.List;
import lombok.Builder;

@Builder
public record RegisterProductCommand(
        Long memberId,
        String name,
        String description,
        int price,
        List<String> productImageNames
) {

    public Product toDomain(Member seller) {
        return Product.builder()
                .name(name)
                .seller(seller)
                .description(description)
                .price(price)
                .productImageNames(productImageNames)
                .build();
    }
}

package com.oneandahalf.backend.trade.query.response;

import com.oneandahalf.backend.product.domain.Product;
import java.util.List;
import lombok.Builder;

@Builder
public record MyTradeProductResponse(
        Long id,
        String name,
        int price,
        List<String> productImageNames
) {
    public static MyTradeProductResponse from(Product product) {
        return MyTradeProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .productImageNames(product.getProductImageNames())
                .build();
    }
}

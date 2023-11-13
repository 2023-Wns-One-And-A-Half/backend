package com.oneandahalf.backend.product.query.response;

import com.oneandahalf.backend.product.domain.Product;
import java.util.List;
import lombok.Builder;

@Builder
public record ProductSearchResponse(
        Long id,
        String name,
        int price,
        List<String> productImageNames
) {

    public static ProductSearchResponse from(Product product) {
        return ProductSearchResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .productImageNames(product.getProductImageNames())
                .build();
    }
}

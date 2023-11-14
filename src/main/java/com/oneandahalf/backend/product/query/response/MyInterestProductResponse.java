package com.oneandahalf.backend.product.query.response;

import com.oneandahalf.backend.product.domain.Product;
import com.oneandahalf.backend.product.domain.interest.InterestProduct;
import java.util.List;
import lombok.Builder;

@Builder
public record MyInterestProductResponse(
        Long id,
        String name,
        int price,
        List<String> productImageNames
) {

    public static MyInterestProductResponse from(InterestProduct interestProduct) {
        Product product = interestProduct.getProduct();
        return MyInterestProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .productImageNames(product.getProductImageNames())
                .build();
    }
}

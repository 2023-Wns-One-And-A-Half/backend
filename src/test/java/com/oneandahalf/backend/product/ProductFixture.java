package com.oneandahalf.backend.product;

import com.oneandahalf.backend.member.domain.Member;
import com.oneandahalf.backend.product.domain.Product;
import java.util.List;

public class ProductFixture {

    public static Product product(Member member) {
        return Product.builder()
                .name("product")
                .description("it is product")
                .price(10_000)
                .productImageNames(List.of("productImage1", "productImage2"))
                .seller(member)
                .build();
    }
}

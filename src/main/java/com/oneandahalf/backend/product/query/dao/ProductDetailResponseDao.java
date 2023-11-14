package com.oneandahalf.backend.product.query.dao;

import com.oneandahalf.backend.product.query.dao.support.ProductQuerySupport;
import com.oneandahalf.backend.product.query.response.ProductDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ProductDetailResponseDao {

    private final ProductQuerySupport productQuerySupport;

    public ProductDetailResponse find(Long id) {
        return ProductDetailResponse.from(productQuerySupport.getWithSellerById(id));
    }
}

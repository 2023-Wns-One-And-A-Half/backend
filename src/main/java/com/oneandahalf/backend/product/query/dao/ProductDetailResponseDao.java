package com.oneandahalf.backend.product.query.dao;

import com.oneandahalf.backend.product.query.dao.support.InterestProductQuerySupport;
import com.oneandahalf.backend.product.query.dao.support.ProductQuerySupport;
import com.oneandahalf.backend.product.query.response.ProductDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ProductDetailResponseDao {

    private final ProductQuerySupport productQuerySupport;
    private final InterestProductQuerySupport interestProductQuerySupport;

    public ProductDetailResponse find(Long productId) {
        ProductDetailResponse response = ProductDetailResponse.from(productQuerySupport.getWithSellerById(productId));
        int interestedCount = interestProductQuerySupport.countByProductId(productId);
        response.setInterestedCount(interestedCount);
        return response;
    }
}

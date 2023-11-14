package com.oneandahalf.backend.product.query.dao.support;

import com.oneandahalf.backend.product.domain.interest.InterestProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterestProductQuerySupport extends JpaRepository<InterestProduct, Long> {

    int countByProductId(Long productId);
}

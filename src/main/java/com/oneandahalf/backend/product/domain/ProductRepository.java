package com.oneandahalf.backend.product.domain;

import com.oneandahalf.backend.common.exception.NotFoundEntityException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    default Product getById(Long id) {
        return findById(id).orElseThrow(() ->
                new NotFoundEntityException("id가 %d인 상품이 존재하지 않습니다.".formatted(id)));
    }
}

package com.oneandahalf.backend.product.query.dao.support;

import com.oneandahalf.backend.common.exception.NotFoundEntityException;
import com.oneandahalf.backend.product.domain.Product;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductQuerySupport extends JpaRepository<Product, Long> {

    @EntityGraph(attributePaths = {"seller"})
    default Product getWithSellerById(Long id) {
        return findWithSellerById(id).orElseThrow(() ->
                new NotFoundEntityException("id가 %d인 상품을 찾을 수 없습니다.".formatted(id))
        );
    }

    Optional<Product> findWithSellerById(Long id);
}

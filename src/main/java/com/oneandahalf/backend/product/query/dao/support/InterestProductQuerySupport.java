package com.oneandahalf.backend.product.query.dao.support;

import com.oneandahalf.backend.product.domain.interest.InterestProduct;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterestProductQuerySupport extends JpaRepository<InterestProduct, Long> {

    int countByProductId(Long productId);

    boolean existsByMemberIdAndProductId(Long memberId, Long productId);

    @EntityGraph(attributePaths = {"member"})
    List<InterestProduct> findAllWithProductByMemberId(Long memberId);
}

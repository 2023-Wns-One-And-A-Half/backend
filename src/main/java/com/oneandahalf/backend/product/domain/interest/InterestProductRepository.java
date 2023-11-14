package com.oneandahalf.backend.product.domain.interest;

import com.oneandahalf.backend.common.exception.NotFoundEntityException;
import com.oneandahalf.backend.member.domain.Member;
import com.oneandahalf.backend.product.domain.Product;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterestProductRepository extends JpaRepository<InterestProduct, Long> {

    boolean existsByMemberAndProduct(Member member, Product product);

    default InterestProduct getByMemberIdAndProductId(Long memberId, Long productId) {
        return findByMemberIdAndProductId(memberId, productId)
                .orElseThrow(() ->
                new NotFoundEntityException("회원(id: %d)이 상품(id: %d)에 관심을 누르지 않았습니다."
                        .formatted(memberId, productId)));
    }

    Optional<InterestProduct> findByMemberIdAndProductId(Long memberId, Long productId);
}

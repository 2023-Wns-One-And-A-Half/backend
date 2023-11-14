package com.oneandahalf.backend.product.domain.interest;

import com.oneandahalf.backend.member.domain.Member;
import com.oneandahalf.backend.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterestProductRepository extends JpaRepository<InterestProduct, Long> {

    boolean existsByMemberAndProduct(Member member, Product product);
}

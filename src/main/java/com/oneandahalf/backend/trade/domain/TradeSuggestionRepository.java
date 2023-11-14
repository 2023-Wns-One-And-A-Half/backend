package com.oneandahalf.backend.trade.domain;

import com.oneandahalf.backend.member.domain.Member;
import com.oneandahalf.backend.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TradeSuggestionRepository extends JpaRepository<TradeSuggestion, Long> {

    boolean existsBySuggesterAndProduct(Member suggester, Product product);
}

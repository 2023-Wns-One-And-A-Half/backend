package com.oneandahalf.backend.trade.domain;

import com.oneandahalf.backend.common.exception.NotFoundEntityException;
import com.oneandahalf.backend.member.domain.Member;
import com.oneandahalf.backend.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TradeSuggestionRepository extends JpaRepository<TradeSuggestion, Long> {

    default TradeSuggestion getById(Long id) {
        return findById(id).orElseThrow(() ->
                new NotFoundEntityException("id가 %d인 거래 제안이 존재하지 않습니다.".formatted(id))
        );
    }

    boolean existsBySuggesterAndProduct(Member suggester, Product product);
}

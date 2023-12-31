package com.oneandahalf.backend.trade.query.dao.support;

import com.oneandahalf.backend.trade.domain.TradeSuggestion;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TradeSuggestionQuerySupport extends JpaRepository<TradeSuggestion, Long> {

    Optional<TradeSuggestion> findBySuggesterIdAndProductId(Long suggesterId, Long productId);

    @EntityGraph(attributePaths = {"suggester"})
    List<TradeSuggestion> findAllWithSuggesterByProductId(Long productId);
}

package com.oneandahalf.backend.trade.domain;

import com.oneandahalf.backend.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TradeRepository extends JpaRepository<Trade, Long> {

    boolean existsByProduct(Product product);
}

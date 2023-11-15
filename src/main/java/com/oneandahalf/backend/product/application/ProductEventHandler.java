package com.oneandahalf.backend.product.application;

import com.oneandahalf.backend.member.domain.blacklist.AddBlacklistEvent;
import com.oneandahalf.backend.product.domain.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProductEventHandler {

    private final ProductRepository productRepository;

    @EventListener(AddBlacklistEvent.class)
    public void deleteAllBlacklistProduct(AddBlacklistEvent event) {
        Long memberId = event.memberId();
        productRepository.deleteAllBySellerId(memberId);
    }
}

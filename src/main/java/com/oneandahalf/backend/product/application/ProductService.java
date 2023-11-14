package com.oneandahalf.backend.product.application;

import com.oneandahalf.backend.member.domain.Member;
import com.oneandahalf.backend.member.domain.MemberRepository;
import com.oneandahalf.backend.product.application.command.RegisterProductCommand;
import com.oneandahalf.backend.product.domain.Product;
import com.oneandahalf.backend.product.domain.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class ProductService {

    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    public Long register(RegisterProductCommand command) {
        Member seller = memberRepository.getById(command.memberId());
        Product product = command.toDomain(seller);
        return productRepository.save(product).getId();
    }

    public void upViewCount(Long productId) {
        Product product = productRepository.getById(productId);
        product.upViewCount();
    }
}

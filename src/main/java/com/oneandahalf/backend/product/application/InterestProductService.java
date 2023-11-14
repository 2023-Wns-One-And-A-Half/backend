package com.oneandahalf.backend.product.application;


import com.oneandahalf.backend.member.domain.Member;
import com.oneandahalf.backend.member.domain.MemberRepository;
import com.oneandahalf.backend.product.application.command.InterestProductCommand;
import com.oneandahalf.backend.product.application.command.UnInterestProductCommand;
import com.oneandahalf.backend.product.domain.Product;
import com.oneandahalf.backend.product.domain.ProductRepository;
import com.oneandahalf.backend.product.domain.interest.InterestProduct;
import com.oneandahalf.backend.product.domain.interest.InterestProductRepository;
import com.oneandahalf.backend.product.domain.interest.InterestProductValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class InterestProductService {

    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final InterestProductRepository interestProductRepository;
    private final InterestProductValidator interestProductValidator;

    public void interest(InterestProductCommand command) {
        Member member = memberRepository.getById(command.memberId());
        Product product = productRepository.getById(command.productId());
        InterestProduct interestProduct = new InterestProduct(member, product);
        interestProduct.interest(interestProductValidator);
        interestProductRepository.save(interestProduct);
    }

    public void unInterest(UnInterestProductCommand command) {
        InterestProduct interestProduct = interestProductRepository
                .getByMemberIdAndProductId(command.memberId(), command.productId());
        interestProductRepository.delete(interestProduct);
    }
}

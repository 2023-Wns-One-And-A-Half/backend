package com.oneandahalf.backend.product.domain.interest;

import com.oneandahalf.backend.member.domain.Member;
import com.oneandahalf.backend.product.domain.Product;
import com.oneandahalf.backend.product.exception.DuplicateInterestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class InterestProductValidator {

    private final InterestProductRepository interestProductRepository;

    public void validateDuplicate(Member member, Product product) {
        if (interestProductRepository.existsByMemberAndProduct(member, product)) {
            throw new DuplicateInterestException();
        }
    }
}

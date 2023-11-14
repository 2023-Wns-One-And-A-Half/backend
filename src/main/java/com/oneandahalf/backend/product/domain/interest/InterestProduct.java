package com.oneandahalf.backend.product.domain.interest;

import com.oneandahalf.backend.common.domain.CommonDomainModel;
import com.oneandahalf.backend.member.domain.Member;
import com.oneandahalf.backend.product.domain.Product;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(
        name = "interest_product",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "interest_product_member_product_unique",
                        columnNames = {"member_id", "product_id"}
                )
        }
)
public class InterestProduct extends CommonDomainModel {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    public InterestProduct(Member member, Product product) {
        this.member = member;
        this.product = product;
    }

    public void interest(InterestProductValidator validator) {
        validator.validateDuplicate(member, product);
    }
}

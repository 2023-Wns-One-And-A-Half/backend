package com.oneandahalf.backend.product.domain;

import static lombok.AccessLevel.PROTECTED;

import com.oneandahalf.backend.common.domain.CommonDomainModel;
import com.oneandahalf.backend.member.domain.Member;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
public class Product extends CommonDomainModel {

    private String name;
    private String description;

    @Embedded
    private Price price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private Member seller;

    private boolean traded;

    private int viewCount;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "product_post_id", nullable = false, updatable = false)
    private List<ProductImageName> productImageNames = new ArrayList<>();

    @Builder
    public Product(String name,
                   String description,
                   int price,
                   Member seller,
                   List<String> productImageNames) {
        this.name = name;
        this.description = description;
        this.price = new Price(price);
        this.seller = seller;
        this.productImageNames = productImageNames.stream()
                .map(ProductImageName::new)
                .toList();
        registerEvent(new RegisterProductEvent(this));
    }

    public void confirmTrade() {
        this.traded = true;
    }

    public int getPrice() {
        return price.getValue();
    }

    public void upViewCount() {
        this.viewCount++;
    }

    public List<String> getProductImageNames() {
        return productImageNames.stream()
                .map(ProductImageName::getName)
                .toList();
    }
}

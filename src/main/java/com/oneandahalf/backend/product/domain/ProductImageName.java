package com.oneandahalf.backend.product.domain;

import com.oneandahalf.backend.common.domain.CommonDomainModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ProductImageName extends CommonDomainModel {

    @Column(nullable = false, unique = true)
    private String name;

    public ProductImageName(String name) {
        this.name = name;
    }
}

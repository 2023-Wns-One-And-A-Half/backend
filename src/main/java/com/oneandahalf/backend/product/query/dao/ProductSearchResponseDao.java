package com.oneandahalf.backend.product.query.dao;

import static com.oneandahalf.backend.member.domain.QMember.member;
import static com.oneandahalf.backend.product.domain.QProduct.product;

import com.oneandahalf.backend.member.domain.ActivityArea;
import com.oneandahalf.backend.product.query.response.ProductSearchResponse;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@RequiredArgsConstructor
@Component
public class ProductSearchResponseDao {

    private final JPAQueryFactory query;

    public Page<ProductSearchResponse> search(ProductSearchCond cond, Pageable pageable) {
        List<ProductSearchResponse> result = query
                .selectFrom(product)
                .join(product.seller, member)
                .where(
                        activityAreaEq(cond.activityArea()),
                        priceContains(cond.minPrice(), cond.maxPrice()),
                        nameContains(cond.name()),
                        notTraded()
                ).orderBy(product.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch()
                .stream()
                .map(ProductSearchResponse::from)
                .toList();
        JPAQuery<Long> countQuery = query.select(product.count())
                .from(product)
                .join(product.seller, member)
                .where(
                        activityAreaEq(cond.activityArea()),
                        priceContains(cond.minPrice(), cond.maxPrice()),
                        nameContains(cond.name()),
                        notTraded()
                );
        return PageableExecutionUtils.getPage(result, pageable, countQuery::fetchOne);
    }

    private BooleanExpression activityAreaEq(ActivityArea activityArea) {
        if (activityArea == null) {
            return null;
        }
        return product.seller.activityArea.eq(activityArea);
    }

    private BooleanExpression priceContains(Integer minPrice, Integer maxPrice) {
        if (minPrice == null && maxPrice == null) {
            return null;
        }
        if (minPrice == null) {
            return product.price.value.loe(maxPrice);
        }
        if (maxPrice == null) {
            return product.price.value.goe(minPrice);
        }
        return product.price.value.between(minPrice, maxPrice);
    }

    private BooleanExpression nameContains(String name) {
        if (ObjectUtils.isEmpty(name)) {
            return null;
        }
        return product.name.containsIgnoreCase(name);
    }

    private BooleanExpression notTraded() {
        return product.traded.isFalse();
    }

    @Builder
    public record ProductSearchCond(
            ActivityArea activityArea,
            Integer minPrice,
            Integer maxPrice,
            String name
    ) {
    }
}


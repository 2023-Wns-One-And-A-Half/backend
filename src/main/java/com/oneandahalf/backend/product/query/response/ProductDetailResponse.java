package com.oneandahalf.backend.product.query.response;

import com.oneandahalf.backend.member.domain.ActivityArea;
import com.oneandahalf.backend.member.domain.Member;
import com.oneandahalf.backend.product.domain.Product;
import java.util.List;
import lombok.Builder;

@Builder
public record ProductDetailResponse(
        Long id,
        String name,
        String description,
        int price,
        List<String> productImageNames,
        SellerInfoResponse sellerInfo
) {

    public static ProductDetailResponse from(Product product) {
        return ProductDetailResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .productImageNames(product.getProductImageNames())
                .sellerInfo(SellerInfoResponse.from(product.getSeller()))
                .build();
    }

    @Builder
    public record SellerInfoResponse(
            Long id,
            String nickname,
            String profileImageName,
            ActivityArea activityArea
    ) {

        public static SellerInfoResponse from(Member seller) {
            return SellerInfoResponse.builder()
                    .id(seller.getId())
                    .nickname(seller.getNickname())
                    .profileImageName(seller.getProfileImageName())
                    .activityArea(seller.getActivityArea())
                    .build();
        }
    }
}

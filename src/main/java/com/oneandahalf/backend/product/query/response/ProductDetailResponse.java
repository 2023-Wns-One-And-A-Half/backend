package com.oneandahalf.backend.product.query.response;

import com.oneandahalf.backend.member.domain.ActivityArea;
import com.oneandahalf.backend.member.domain.Member;
import com.oneandahalf.backend.product.domain.Product;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductDetailResponse {

    private Long id;
    private String name;
    private String description;
    private int price;
    private int interestedCount;
    private boolean interested;
    private List<String> productImageNames;
    private SellerInfoResponse sellerInfo;

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

    public void setInterestedCount(int interestedCount) {
        this.interestedCount = interestedCount;
    }

    public void setInterested(boolean interested) {
        this.interested = interested;
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

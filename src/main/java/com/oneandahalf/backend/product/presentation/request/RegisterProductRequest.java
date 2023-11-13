package com.oneandahalf.backend.product.presentation.request;

import com.oneandahalf.backend.product.application.command.RegisterProductCommand;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Builder;

@Builder
public record RegisterProductRequest(
        @NotEmpty(message = "상품 이름은 비어있으면 안됩니다.") String name,
        @NotEmpty(message = "상품 설명은 비어있으면 안됩니다.") String description,
        int price,
        @NotNull List<String> productImageNames
) {

    public RegisterProductCommand toCommand(Long memberId) {
        return RegisterProductCommand.builder()
                .memberId(memberId)
                .name(name)
                .description(description)
                .price(price)
                .productImageNames(productImageNames)
                .build();
    }
}

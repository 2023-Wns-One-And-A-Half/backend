package com.oneandahalf.backend.product.presentation;

import com.oneandahalf.backend.member.presentation.support.Auth;
import com.oneandahalf.backend.product.application.ProductService;
import com.oneandahalf.backend.product.presentation.request.RegisterProductRequest;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/products")
@RestController
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Void> register(
            @Auth Long memberId,
            @RequestBody RegisterProductRequest request
    ) {
        Long productId = productService.register(request.toCommand(memberId));
        return ResponseEntity
                .created(URI.create("/products/" + productId))
                .build();
    }
}

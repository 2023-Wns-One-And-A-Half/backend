package com.oneandahalf.backend.product.presentation;

import com.oneandahalf.backend.common.page.PageResponse;
import com.oneandahalf.backend.member.presentation.support.Auth;
import com.oneandahalf.backend.product.application.ProductService;
import com.oneandahalf.backend.product.presentation.request.RegisterProductRequest;
import com.oneandahalf.backend.product.query.ProductQueryService;
import com.oneandahalf.backend.product.query.dao.ProductSearchResponseDao.ProductSearchCond;
import com.oneandahalf.backend.product.query.response.ProductSearchResponse;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/products")
@RestController
public class ProductController {

    private final ProductService productService;
    private final ProductQueryService productQueryService;

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

    @GetMapping
    public ResponseEntity<PageResponse<ProductSearchResponse>> search(
            @ModelAttribute ProductSearchCond cond,
            @PageableDefault(size = 30) Pageable pageable
    ) {
        PageResponse<ProductSearchResponse> result = productQueryService.search(cond, pageable);
        return ResponseEntity.ok(result);
    }
}

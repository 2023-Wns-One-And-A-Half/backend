package com.oneandahalf.backend.product.presentation;

import static org.springframework.http.HttpHeaders.SET_COOKIE;

import com.oneandahalf.backend.common.image.ImageUploadClient;
import com.oneandahalf.backend.common.page.PageResponse;
import com.oneandahalf.backend.member.presentation.support.Auth;
import com.oneandahalf.backend.member.presentation.support.OptionalAuth;
import com.oneandahalf.backend.product.application.ProductService;
import com.oneandahalf.backend.product.presentation.request.RegisterProductRequest;
import com.oneandahalf.backend.product.query.ProductQueryService;
import com.oneandahalf.backend.product.query.dao.ProductSearchResponseDao.ProductSearchCond;
import com.oneandahalf.backend.product.query.response.ProductDetailResponse;
import com.oneandahalf.backend.product.query.response.ProductSearchResponse;
import jakarta.servlet.http.HttpServletResponse;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.server.Cookie.SameSite;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/products")
@RestController
public class ProductController {

    private final ImageUploadClient imageUploadClient;
    private final ProductService productService;
    private final ProductQueryService productQueryService;

    @PostMapping
    public ResponseEntity<Void> register(
            @Auth Long memberId,
            @RequestBody RegisterProductRequest request
    ) {
        List<String> productImageNames = imageUploadClient.upload(request.productImages());
        Long productId = productService.register(request.toCommand(memberId, productImageNames));
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

    @GetMapping("/{id}")
    public ResponseEntity<ProductDetailResponse> findDetail(
            @OptionalAuth Long memberId,
            @PathVariable("id") Long productId,
            @CookieValue(name = "VIEW_SESSION", required = false) String viewSessionCookie,
            HttpServletResponse response
    ) {
        if (viewSessionCookie == null) {
            productService.upViewCount(productId);
            ResponseCookie cookie = ResponseCookie.from("VIEW_SESSION", UUID.randomUUID().toString())
                    .httpOnly(true)
                    .secure(true)
                    .sameSite(SameSite.NONE.attributeValue())
                    .maxAge((long) 60 * 60 * 24)
                    .path("/")
                    .build();
            response.addHeader(SET_COOKIE, cookie.toString());
        }
        return ResponseEntity.ok(productQueryService.find(memberId, productId));
    }
}

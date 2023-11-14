package com.oneandahalf.backend.product.query;

import com.oneandahalf.backend.common.page.PageResponse;
import com.oneandahalf.backend.product.query.dao.ProductDetailResponseDao;
import com.oneandahalf.backend.product.query.dao.ProductSearchResponseDao;
import com.oneandahalf.backend.product.query.dao.ProductSearchResponseDao.ProductSearchCond;
import com.oneandahalf.backend.product.query.response.ProductDetailResponse;
import com.oneandahalf.backend.product.query.response.ProductSearchResponse;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ProductQueryService {

    private final ProductDetailResponseDao productDetailResponseDao;
    private final ProductSearchResponseDao productSearchResponseDao;

    public ProductDetailResponse find(@Nullable Long memberId, Long productId) {
        return productDetailResponseDao.find(memberId, productId);
    }

    public PageResponse<ProductSearchResponse> search(ProductSearchCond cond, Pageable pageable) {
        return PageResponse.from(productSearchResponseDao.search(cond, pageable));
    }
}

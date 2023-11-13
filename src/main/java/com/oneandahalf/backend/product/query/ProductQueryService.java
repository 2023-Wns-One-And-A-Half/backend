package com.oneandahalf.backend.product.query;

import com.oneandahalf.backend.common.page.PageResponse;
import com.oneandahalf.backend.product.query.dao.ProductSearchResponseDao;
import com.oneandahalf.backend.product.query.dao.ProductSearchResponseDao.ProductSearchCond;
import com.oneandahalf.backend.product.query.response.ProductSearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ProductQueryService {

    private final ProductSearchResponseDao productSearchResponseDao;

    public PageResponse<ProductSearchResponse> search(ProductSearchCond cond, Pageable pageable) {
        return PageResponse.from(productSearchResponseDao.search(cond, pageable));
    }
}

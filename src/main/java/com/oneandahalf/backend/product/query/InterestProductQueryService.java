package com.oneandahalf.backend.product.query;

import com.oneandahalf.backend.product.query.dao.MyInterestProductResponseDao;
import com.oneandahalf.backend.product.query.response.MyInterestProductResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class InterestProductQueryService {

    private final MyInterestProductResponseDao myInterestProductResponseDao;

    public List<MyInterestProductResponse> findMy(Long memberId) {
        return myInterestProductResponseDao.find(memberId);
    }
}

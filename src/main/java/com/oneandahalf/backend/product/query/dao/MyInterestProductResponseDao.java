package com.oneandahalf.backend.product.query.dao;

import com.oneandahalf.backend.product.query.dao.support.InterestProductQuerySupport;
import com.oneandahalf.backend.product.query.response.MyInterestProductResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MyInterestProductResponseDao {

    private final InterestProductQuerySupport interestProductQuerySupport;

    public List<MyInterestProductResponse> find(Long memberId) {
        return interestProductQuerySupport.findAllWithProductByMemberId(memberId)
                .stream()
                .map(MyInterestProductResponse::from)
                .toList();
    }
}

package com.oneandahalf.backend.keyword.query.dao;

import com.oneandahalf.backend.keyword.query.dao.support.KeywordQuerySupport;
import com.oneandahalf.backend.keyword.query.response.MyKeywordResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MyKeywordResponseDao {

    private final KeywordQuerySupport keywordQuerySupport;

    public List<MyKeywordResponse> find(Long memberId) {
        return keywordQuerySupport.findAllByMemberId(memberId)
                .stream()
                .map(MyKeywordResponse::from)
                .toList();
    }
}

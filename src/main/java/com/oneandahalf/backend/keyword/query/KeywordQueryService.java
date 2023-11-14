package com.oneandahalf.backend.keyword.query;

import com.oneandahalf.backend.keyword.query.dao.MyKeywordResponseDao;
import com.oneandahalf.backend.keyword.query.response.MyKeywordResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class KeywordQueryService {

    private final MyKeywordResponseDao myKeywordResponseDao;

    public List<MyKeywordResponse> findMine(Long memberId) {
        return myKeywordResponseDao.find(memberId);
    }
}

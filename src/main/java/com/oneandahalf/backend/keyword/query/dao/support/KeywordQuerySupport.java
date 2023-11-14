package com.oneandahalf.backend.keyword.query.dao.support;

import com.oneandahalf.backend.keyword.domain.Keyword;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KeywordQuerySupport extends JpaRepository<Keyword, Long> {
    
    List<Keyword> findAllByMemberId(Long memberId);
}

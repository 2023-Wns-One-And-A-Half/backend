package com.oneandahalf.backend.keyword.domain;

import com.oneandahalf.backend.common.exception.NotFoundEntityException;
import com.oneandahalf.backend.member.domain.Member;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {

    default Keyword getById(Long id) {
        return findById(id).orElseThrow(() ->
                new NotFoundEntityException("id가 %d인 키워드가 존재하지 않습니다.".formatted(id))
        );
    }

    boolean existsByMemberAndContent(Member member, String content);

    void deleteByIdAndMemberId(Long id, Long memberId);

    @Query("SELECT k FROM Keyword k WHERE :content LIKE CONCAT('%', k.content, '%')")
    List<Keyword> findAllByContainsContent(@Param("content") String content);
}

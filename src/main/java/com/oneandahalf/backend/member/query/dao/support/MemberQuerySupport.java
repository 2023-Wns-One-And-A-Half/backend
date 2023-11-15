package com.oneandahalf.backend.member.query.dao.support;

import com.oneandahalf.backend.common.exception.NotFoundEntityException;
import com.oneandahalf.backend.member.domain.Member;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemberQuerySupport extends JpaRepository<Member, Long> {

    default Member getById(Long id) {
        return findById(id).orElseThrow(() ->
                new NotFoundEntityException("id가 %d인 회원을 찾을 수 없습니다.".formatted(id))
        );
    }

    @Query("SELECT m FROM Member m WHERE m.id NOT IN (:ids)")
    List<Member> findAllByIdNotIn(List<Long> ids);
}

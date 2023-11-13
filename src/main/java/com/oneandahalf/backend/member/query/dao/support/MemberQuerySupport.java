package com.oneandahalf.backend.member.query.dao.support;

import com.oneandahalf.backend.member.domain.Member;
import java.util.NoSuchElementException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberQuerySupport extends JpaRepository<Member, Long> {

    default Member getById(Long id) {
        return findById(id).orElseThrow(() ->
                new NoSuchElementException("id가 %d인 회원을 찾을 수 없습니다.".formatted(id))
        );
    }
}

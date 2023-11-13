package com.oneandahalf.backend.member.domain;

import com.oneandahalf.backend.common.exception.NotFoundEntityException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    default Member getById(Long id) {
        return findById(id).orElseThrow(() ->
                new NotFoundEntityException("id가 %d인 회원이 존재하지 않습니다.".formatted(id))
        );
    }

    boolean existsByUsername(Username username);
}

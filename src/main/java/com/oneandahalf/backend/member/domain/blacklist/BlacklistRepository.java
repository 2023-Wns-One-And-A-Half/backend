package com.oneandahalf.backend.member.domain.blacklist;

import com.oneandahalf.backend.common.exception.NotFoundEntityException;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlacklistRepository extends JpaRepository<Blacklist, Long> {

    default Blacklist getByMemberId(Long memberId) {
        return findByMemberId(memberId).orElseThrow(() ->
                new NotFoundEntityException("회원 id가 %d인 회원은 블랙리스트가 아닙니다."));
    }

    Optional<Blacklist> findByMemberId(Long memberId);
}

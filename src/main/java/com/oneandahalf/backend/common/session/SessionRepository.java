package com.oneandahalf.backend.common.session;

import com.oneandahalf.backend.common.session.Session.SessionType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<Session, String> {
    void deleteByMemberIdAndType(Long memberId, SessionType type);
}

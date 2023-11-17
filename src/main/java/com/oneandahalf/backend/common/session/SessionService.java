package com.oneandahalf.backend.common.session;

import com.oneandahalf.backend.common.session.Session.SessionType;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class SessionService {

    private final SessionRepository sessionRepository;

    public String save(Long memberId, SessionType type) {
        return sessionRepository.save(new Session(memberId, type))
                .getId();
    }

    @Transactional(readOnly = true)
    public Optional<Session> getSession(String sessionId) {
        if (sessionId == null) {
            return Optional.empty();
        }
        return sessionRepository.findById(sessionId);
    }
}

package com.oneandahalf.backend.member.presentation.support;

import static com.oneandahalf.backend.member.presentation.support.AuthConstant.SESSION_ATTRIBUTE_MEMBER_ID;

import com.oneandahalf.backend.common.session.Session;
import com.oneandahalf.backend.common.session.Session.SessionType;
import com.oneandahalf.backend.common.session.SessionService;
import com.oneandahalf.backend.member.domain.blacklist.BlacklistRepository;
import com.oneandahalf.backend.member.exception.ForbiddenBlacklistException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@RequiredArgsConstructor
@Component
public class OptionalAuthInterceptor implements HandlerInterceptor {

    private final SessionService sessionService;
    private final BlacklistRepository blacklistRepository;
    private final AuthContext authContext;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        sessionService.getSession(request.getHeader("JSESSIONID"))
                .filter(it -> it.getType() == SessionType.MEMBER)
                .map(Session::getMemberId)
                .ifPresent(id -> {
                    checkBlackList(request, id);
                    authContext.setMemberId(id);
                });
        return true;
    }

    private void checkBlackList(HttpServletRequest request, Long id) {
        if (blacklistRepository.findByMemberId(id).isPresent()) {
            Optional.ofNullable(request.getSession(false))
                    .ifPresent(session -> {
                        session.removeAttribute(SESSION_ATTRIBUTE_MEMBER_ID);
                        session.invalidate();
                    });
            throw new ForbiddenBlacklistException();
        }
    }
}

package com.oneandahalf.backend.admin.auth.presentation.support;

import com.oneandahalf.backend.common.session.Session;
import com.oneandahalf.backend.common.session.Session.SessionType;
import com.oneandahalf.backend.common.session.SessionService;
import com.oneandahalf.backend.member.exception.NoAuthenticationSessionException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.servlet.HandlerInterceptor;

@RequiredArgsConstructor
@Component
public class AdminAuthInterceptor implements HandlerInterceptor {

    private final SessionService sessionService;
    private final AdminAuthContext adminAuthContext;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (CorsUtils.isPreFlightRequest(request)) {
            return true;
        }
        sessionService.getSession(request.getHeader("JSESSIONID"))
                .filter(it -> it.getType() == SessionType.ADMIN)
                .map(Session::getMemberId)
                .ifPresent(adminAuthContext::setAdminId);
        if (adminAuthContext.unAuthenticated()) {
            throw new NoAuthenticationSessionException();
        }
        return true;
    }
}

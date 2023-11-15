package com.oneandahalf.backend.admin.auth.presentation.support;

import static com.oneandahalf.backend.admin.auth.presentation.support.AdminAuthConstant.SESSION_ATTRIBUTE_ADMIN_ID;

import com.oneandahalf.backend.member.exception.NoAuthenticationSessionException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@RequiredArgsConstructor
@Component
public class AdminAuthInterceptor implements HandlerInterceptor {

    private final AdminAuthContext adminAuthContext;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        Optional.ofNullable(request.getSession(false))
                .map(it -> it.getAttribute(SESSION_ATTRIBUTE_ADMIN_ID))
                .map(id -> (Long) id)
                .ifPresent(adminAuthContext::setAdminId);
        if (adminAuthContext.unAuthenticated()) {
            throw new NoAuthenticationSessionException();
        }
        return true;
    }
}

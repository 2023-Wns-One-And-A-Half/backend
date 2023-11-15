package com.oneandahalf.backend.admin.auth.presentation.support;

import com.oneandahalf.backend.member.exception.NoAuthenticationSessionException;
import com.oneandahalf.backend.member.presentation.support.AuthContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@RequiredArgsConstructor
@Component
public class AdminAuthInterceptor implements HandlerInterceptor {

    private final AuthContext authContext;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (authContext.unAuthenticated()) {
            throw new NoAuthenticationSessionException();
        }
        return true;
    }
}

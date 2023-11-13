package com.oneandahalf.backend.member.presentation.support;

import static com.oneandahalf.backend.member.presentation.support.AuthConstant.SESSION_ATTRIBUTE_MEMBER_ID;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@RequiredArgsConstructor
@Component
public class OptionalAuthInterceptor implements HandlerInterceptor {

    private final AuthContext authContext;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        Optional.ofNullable(request.getSession(false))
                .map(it -> it.getAttribute(SESSION_ATTRIBUTE_MEMBER_ID))
                .map(id -> (Long) id)
                .ifPresent(authContext::setMemberId);
        return true;
    }
}

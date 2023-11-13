package com.oneandahalf.backend.common.presentation;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Set;
import lombok.Builder;
import org.springframework.http.HttpMethod;
import org.springframework.util.PathMatcher;

@Builder
public record UriAndMethodCondition(
        Set<String> uriPatterns,
        Set<HttpMethod> httpMethods
) {

    public boolean match(PathMatcher pathMatcher, HttpServletRequest request) {
        return matchURI(pathMatcher, request)
                && matchMethod(request);
    }

    private boolean matchURI(PathMatcher pathMatcher, HttpServletRequest request) {
        return uriPatterns.stream()
                .anyMatch(pattern -> pathMatcher.match(pattern, request.getRequestURI()));
    }

    private boolean matchMethod(HttpServletRequest request) {
        return httpMethods.contains(HttpMethod.valueOf(request.getMethod()));
    }
}

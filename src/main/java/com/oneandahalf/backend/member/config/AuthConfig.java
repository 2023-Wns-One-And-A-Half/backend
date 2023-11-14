package com.oneandahalf.backend.member.config;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

import com.oneandahalf.backend.common.presentation.UriAndMethodCondition;
import com.oneandahalf.backend.member.presentation.support.AuthArgumentResolver;
import com.oneandahalf.backend.member.presentation.support.AuthInterceptor;
import com.oneandahalf.backend.member.presentation.support.OptionalAuthArgumentResolver;
import com.oneandahalf.backend.member.presentation.support.OptionalAuthInterceptor;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Configuration
public class AuthConfig implements WebMvcConfigurer {

    private final AuthInterceptor authInterceptor;
    private final AuthArgumentResolver authArgumentResolver;
    private final OptionalAuthInterceptor optionalAuthInterceptor;
    private final OptionalAuthArgumentResolver optionalAuthArgumentResolver;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(optionalAuthInterceptor)
                .addPathPatterns("/**")
                .order(0);
        registry.addInterceptor(setUpAuthInterceptor())
                .addPathPatterns("/**")
                .order(1);
    }

    private AuthInterceptor setUpAuthInterceptor() {
        authInterceptor.setNoAuthRequiredConditions(
                UriAndMethodCondition.builder()
                        .uriPatterns(Set.of("/members"))
                        .httpMethods(Set.of(POST))
                        .build(),
                UriAndMethodCondition.builder()
                        .uriPatterns(Set.of("/members/login"))
                        .httpMethods(Set.of(POST))
                        .build(),
                UriAndMethodCondition.builder()
                        .uriPatterns(Set.of("/products/**"))
                        .httpMethods(Set.of(GET))
                        .build()
        );
        return authInterceptor;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authArgumentResolver);
        resolvers.add(optionalAuthArgumentResolver);
    }
}

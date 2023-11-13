package com.oneandahalf.backend.member.config;

import com.oneandahalf.backend.member.presentation.support.AuthArgumentResolver;
import com.oneandahalf.backend.member.presentation.support.AuthInterceptor;
import com.oneandahalf.backend.member.presentation.support.OptionalAuthArgumentResolver;
import com.oneandahalf.backend.member.presentation.support.OptionalAuthInterceptor;
import java.util.List;
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
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/members/my")
                .order(1);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authArgumentResolver);
        resolvers.add(optionalAuthArgumentResolver);
    }
}

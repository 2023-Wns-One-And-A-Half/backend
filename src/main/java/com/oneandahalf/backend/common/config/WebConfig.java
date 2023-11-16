package com.oneandahalf.backend.common.config;

import static org.springframework.http.HttpMethod.*;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*") // HTTPS 를 사용하지 않으므로 편의를 위해 *로 설정
                .allowedMethods(
                        OPTIONS.name(),
                        GET.name(),
                        POST.name(),
                        PUT.name(),
                        DELETE.name(),
                        PATCH.name()
                )
                .exposedHeaders("*");
    }
}

package com.oneandahalf.backend.common.config;

import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.OPTIONS;
import static org.springframework.http.HttpMethod.PATCH;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000", "http://localhost:5500",
                        "http://127.0.0.1:3000", "http://127.0.0.1:5500")
                .allowedHeaders("*")
                .allowedMethods(
                        OPTIONS.name(),
                        GET.name(),
                        POST.name(),
                        PUT.name(),
                        DELETE.name(),
                        PATCH.name()
                )
                .allowCredentials(true)
                .exposedHeaders("*");
    }
}

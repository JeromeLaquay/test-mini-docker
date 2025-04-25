package com.example.apigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfig {
    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/api/auth/**")
                        .uri("http://users-service:8081"))
                .route(r -> r.path("/api/users/**")
                        .uri("http://users-service:8081"))
                .build();
    }
} 
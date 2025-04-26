package com.jeroka.gateway.config;

import com.jeroka.gateway.security.JwtAuthenticationFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;

    public RouteConfig(JwtAuthenticationFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("subscription-service-public", r -> r
                        .path( "/api/plans/**")
                        .uri("http://localhost:8084"))
                .route("subscription-service", r -> r
                        .path("/api/subscriptions/**")
                        .filters(f -> f.filter(jwtAuthFilter))
                        .uri("http://localhost:8084"))
                .route("auth-service", r -> r
                        .path("/api/auth/**", "/api/test/**")
                        .uri("http://localhost:8081"))
                .route("album-service", r -> r
                        .path("/api/albums/**")
                        .uri("http://localhost:8083"))
                .build();
    }
} 
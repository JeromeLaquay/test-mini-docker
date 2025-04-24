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
                .route(r -> r.path("/auth/**")
                        .uri("lb://AUTH-SERVICE"))
                .route(r -> r.path("/users/**")
                        .uri("lb://USERS-SERVICE"))
                .route(r -> r.path("/products/**")
                        .uri("lb://PRODUCTS-SERVICE"))
                .route(r -> r.path("/v2/api-docs", "/swagger-ui.html")
                        .uri("http://localhost:8080"))
                .build();
    }
} 
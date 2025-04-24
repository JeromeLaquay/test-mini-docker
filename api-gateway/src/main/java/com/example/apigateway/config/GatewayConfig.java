package com.example.apigateway.config;

import com.example.apigateway.filter.JwtAuthenticationFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    private final JwtProperties jwtProperties;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public GatewayConfig(JwtProperties jwtProperties, JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtProperties = jwtProperties;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                // Route pour le service d'authentification
                .route("auth-service", r -> r
                        .path("/api/auth/**")
                        .uri("lb://auth-service"))
                
                // Route pour le service de gestion des utilisateurs
                .route("user-service", r -> r
                        .path("/api/users/**")
                        .filters(f -> f
                                .filter(jwtAuthenticationFilter.apply(new JwtAuthenticationFilter.Config())))
                        .uri("lb://user-service"))
                
                // Route pour le service de gestion des produits
                .route("product-service", r -> r
                        .path("/api/products/**")
                        .filters(f -> f
                                .filter(jwtAuthenticationFilter.apply(new JwtAuthenticationFilter.Config())))
                        .uri("lb://product-service"))
                
                .build();
    }
} 
package com.jeroka.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@SpringBootApplication
@RestController
public class ApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("auth_fallback_route", r -> r
                        .path("/fallback/auth")
                        .filters(f -> f
                                .modifyResponseBody(String.class, String.class,
                                    (exchange, s) -> Mono.just("{\"message\":\"Auth Service is not available.\",\"status\":503}")))
                        .uri("http://auth-service:8081"))
                .route("album_fallback_route", r -> r
                        .path("/fallback/album")
                        .filters(f -> f
                                .modifyResponseBody(String.class, String.class,
                                    (exchange, s) -> Mono.just("{\"message\":\"Album Service is not available.\",\"status\":503}")))
                        .uri("http://album-service:8083"))
                .route("subscription_fallback_route", r -> r
                        .path("/fallback/subscription")
                        .filters(f -> f
                                .modifyResponseBody(String.class, String.class,
                                    (exchange, s) -> Mono.just("{\"message\":\"Subscription Service is not available.\",\"status\":503}")))
                        .uri("http://subscription-service:8084"))
                .build();
    }
} 
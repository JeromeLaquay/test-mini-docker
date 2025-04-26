package com.jeroka.gateway.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationFilter implements GatewayFilter {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        if (!request.getHeaders().containsKey("Authorization")) {
            return onError(exchange, "No Authorization header", HttpStatus.UNAUTHORIZED);
        }

        String authHeader = request.getHeaders().get("Authorization").get(0);
        String[] parts = authHeader.split(" ");
        if (parts.length != 2 || !"Bearer".equals(parts[0])) {
            return onError(exchange, "Incorrect Authorization structure", HttpStatus.UNAUTHORIZED);
        }

        String token = parts[1];
        try {
            validateToken(token);
        } catch (JwtException | IllegalArgumentException e) {
            return onError(exchange, e.getMessage(), HttpStatus.UNAUTHORIZED);
        }

        // Propager le token JWT validé vers les microservices
        ServerHttpRequest modifiedRequest = exchange.getRequest().mutate()
                .header("Authorization", "Bearer " + token)
                .build();
        
        return chain.filter(exchange.mutate().request(modifiedRequest).build());
    }

    private void validateToken(String token) throws JwtException, IllegalArgumentException {
        Jwts.parserBuilder().setSigningKey(jwtSecret.getBytes()).build().parseClaimsJws(token);
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        exchange.getResponse().setStatusCode(httpStatus);
        return exchange.getResponse().setComplete();
    }
} 
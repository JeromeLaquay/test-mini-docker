package com.jeroka.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * Filtre global pour gérer les requêtes CORS préliminaires (OPTIONS)
 */
@Configuration
public class CorsGlobalFilter implements GlobalFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        
        // Gestion des requêtes préliminaires OPTIONS
        if (request.getMethod() == HttpMethod.OPTIONS) {
            ServerHttpResponse response = exchange.getResponse();
            HttpHeaders headers = response.getHeaders();
            
            headers.add("Access-Control-Allow-Origin", "*");
            headers.add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, PATCH, OPTIONS");
            headers.add("Access-Control-Allow-Headers", "Authorization, Content-Type, X-Requested-With");
            headers.add("Access-Control-Max-Age", "3600");
            headers.add("Access-Control-Allow-Credentials", "true");
            
            response.setStatusCode(HttpStatus.OK);
            return Mono.empty();
        }
        
        return chain.filter(exchange);
    }
} 
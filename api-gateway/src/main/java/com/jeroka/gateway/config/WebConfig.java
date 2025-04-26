package com.jeroka.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

/**
 * Configuration CORS pour l'API Gateway
 */
@Configuration
public class WebConfig {

    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setMaxAge(3600L);
        corsConfig.setAllowedOrigins(Arrays.asList("*"));
        corsConfig.setAllowedMethods(Arrays.asList("*"));
        corsConfig.setAllowedHeaders(Arrays.asList("*"));
        corsConfig.setAllowCredentials(true);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);
        
        return new CorsWebFilter(source);
    }
} 
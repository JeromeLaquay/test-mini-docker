package com.jeroka.gateway.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;

@RestController
@RequestMapping("/api")
public class ProxyController {

    @Value("${ROUTE_AUTH_SERVICE:http://localhost:8081}")
    private String authServiceUrl;

    @Value("${ROUTE_ALBUM_SERVICE:http://localhost:8083}")
    private String albumServiceUrl;

    @Value("${ROUTE_SUBSCRIPTION_SERVICE:http://localhost:8084}")
    private String subscriptionServiceUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    @RequestMapping(value = "/**", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH, RequestMethod.OPTIONS})
    public ResponseEntity<?> proxyAll(HttpServletRequest request) throws IOException {
        String path = request.getRequestURI();
        byte[] body = null;
        if (!request.getMethod().equals("GET") && !request.getMethod().equals("DELETE")) {
            body = request.getInputStream().readAllBytes();
        }
        System.out.println("Path: " + path);
        if (path.startsWith("/api/auth") || path.startsWith("/api/test")) {
            System.out.println("Auth service URL: " + authServiceUrl);
            return forward(request, body, authServiceUrl);
        } else if (path.startsWith("/api/albums")) {
            System.out.println("Album service URL: " + albumServiceUrl);
            return forward(request, body, albumServiceUrl);
        } else if (path.startsWith("/api/subscriptions") || path.startsWith("/api/plans")) {
            System.out.println("Subscription service URL: " + subscriptionServiceUrl);
            return forward(request, body, subscriptionServiceUrl);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No route for " + path);
    }

    private ResponseEntity<?> forward(HttpServletRequest request, byte[] body, String serviceUrl) {
        String url = serviceUrl + request.getRequestURI() + (request.getQueryString() != null ? "?" + request.getQueryString() : "");

        HttpHeaders headers = new HttpHeaders();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            if (!headerName.equalsIgnoreCase("host")) {
                Enumeration<String> values = request.getHeaders(headerName);
                while (values.hasMoreElements()) {
                    headers.add(headerName, values.nextElement());
                }
            }
        }
        if (!headers.containsKey("Content-Type") && request.getContentType() != null) {
            headers.set("Content-Type", request.getContentType());
        }

        HttpMethod method = HttpMethod.valueOf(request.getMethod());
        HttpEntity<byte[]> entity = new HttpEntity<>(body, headers);

        return restTemplate.exchange(url, method, entity, String.class);
    }
} 
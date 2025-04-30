package com.jeroka.gateway.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpStatus;

import java.util.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
public class AggregatedDocsController {

    @Value("${ROUTE_INVOICE_SERVICE}")
    private String routeInvoiceService;

    @Value("${ROUTE_AUTH_SERVICE}")
    private String routeAuthService;

    @Value("${ROUTE_ALBUM_SERVICE}")
    private String routeAlbumService;

    @Value("${ROUTE_SUBSCRIPTION_SERVICE}")
    private String routeSubscriptionService;

    @Value("${ROUTE_PAYMENT_SERVICE}")
    private String routePaymentService;
    

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    // Liste des URLs des microservices à agréger
    private final List<String> docsUrls = Arrays.asList(
        routeAuthService + "/v3/api-docs",
        routeAlbumService + "/v3/api-docs",
        routeSubscriptionService + "/v3/api-docs",
        routeInvoiceService + "/v3/api-docs",
        routePaymentService + "/v3/api-docs"
    );

    @GetMapping("/v3/aggregated-api-docs")
    public ResponseEntity<?> aggregateDocs() {
        try {
            ObjectNode merged = objectMapper.createObjectNode();
            merged.put("openapi", "3.0.1");
            merged.putObject("info").put("title", "Aggregated API Docs").put("version", "v1");
            ObjectNode pathsNode = merged.putObject("paths");
            ObjectNode componentsNode = merged.putObject("components");

            for (String url : docsUrls) {
                Map doc = restTemplate.getForObject(url, Map.class);
                if (doc == null) continue;
                // Merge paths
                Map<String, Object> paths = (Map<String, Object>) doc.get("paths");
                if (paths != null) {
                    for (Map.Entry<String, Object> entry : paths.entrySet()) {
                        pathsNode.set(entry.getKey(), objectMapper.valueToTree(entry.getValue()));
                    }
                }
                // Merge components.schemas
                Map<String, Object> components = (Map<String, Object>) doc.get("components");
                if (components != null) {
                    Map<String, Object> schemas = (Map<String, Object>) components.get("schemas");
                    if (schemas != null) {
                        ObjectNode mergedSchemas = (ObjectNode) componentsNode.with("schemas");
                        for (Map.Entry<String, Object> entry : schemas.entrySet()) {
                            mergedSchemas.set(entry.getKey(), objectMapper.valueToTree(entry.getValue()));
                        }
                    }
                    // (optionnel) merge d'autres sous-éléments de components si besoin
                }
            }
            return ResponseEntity.ok(merged);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error aggregating docs: " + e.getMessage());
        }
    }
}
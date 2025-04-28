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

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    // Liste des URLs des microservices à agréger
    private final List<String> docsUrls = Arrays.asList(
        "http://auth-service:8081/v3/api-docs",
        "http://album-service:8083/v3/api-docs",
        "http://subscription-service:8084/v3/api-docs"
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
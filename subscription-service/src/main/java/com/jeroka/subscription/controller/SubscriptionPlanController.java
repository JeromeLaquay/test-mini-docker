package com.jeroka.subscription.controller;

import com.jeroka.subscription.dto.SubscriptionPlanDto;
import com.jeroka.subscription.model.SubscriptionType;
import com.jeroka.subscription.service.SubscriptionPlanService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Contrôleur REST pour la gestion des plans d'abonnement.
 */
@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/plans")
public class SubscriptionPlanController {

    private final SubscriptionPlanService subscriptionPlanService;

    @Autowired
    public SubscriptionPlanController(SubscriptionPlanService subscriptionPlanService) {
        this.subscriptionPlanService = subscriptionPlanService;
    }

    /**
     * Crée un nouveau plan d'abonnement.
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SubscriptionPlanDto> createSubscriptionPlan(@Valid @RequestBody SubscriptionPlanDto subscriptionPlanDto) {
        SubscriptionPlanDto createdPlan = subscriptionPlanService.createSubscriptionPlan(subscriptionPlanDto);
        return new ResponseEntity<>(createdPlan, HttpStatus.CREATED);
    }

    /**
     * Récupère un plan d'abonnement par son ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<SubscriptionPlanDto> getSubscriptionPlan(@PathVariable Long id) {
        SubscriptionPlanDto plan = subscriptionPlanService.getSubscriptionPlanById(id);
        return ResponseEntity.ok(plan);
    }

    /**
     * Récupère un plan d'abonnement par son nom.
     */
    @GetMapping("/name/{name}")
    public ResponseEntity<SubscriptionPlanDto> getSubscriptionPlanByName(@PathVariable String name) {
        SubscriptionPlanDto plan = subscriptionPlanService.getSubscriptionPlanByName(name);
        return ResponseEntity.ok(plan);
    }

    /**
     * Récupère tous les plans d'abonnement.
     */
    @GetMapping
    public ResponseEntity<List<SubscriptionPlanDto>> getAllSubscriptionPlans() {
        List<SubscriptionPlanDto> plans = subscriptionPlanService.getAllSubscriptionPlans();
        return ResponseEntity.ok(plans);
    }

    /**
     * Récupère tous les plans d'abonnement actifs.
     */
    @GetMapping("/active")
    public ResponseEntity<List<SubscriptionPlanDto>> getActiveSubscriptionPlans() {
        List<SubscriptionPlanDto> activePlans = subscriptionPlanService.getActiveSubscriptionPlans();
        return ResponseEntity.ok(activePlans);
    }

    /**
     * Récupère les plans d'abonnement par type.
     */
    @GetMapping("/type/{type}")
    public ResponseEntity<List<SubscriptionPlanDto>> getSubscriptionPlansByType(@PathVariable SubscriptionType type) {
        List<SubscriptionPlanDto> plans = subscriptionPlanService.getSubscriptionPlansByType(type);
        return ResponseEntity.ok(plans);
    }

    /**
     * Récupère les plans d'abonnement actifs par type.
     */
    @GetMapping("/type/{type}/active")
    public ResponseEntity<List<SubscriptionPlanDto>> getActiveSubscriptionPlansByType(@PathVariable SubscriptionType type) {
        List<SubscriptionPlanDto> activePlansByType = subscriptionPlanService.getActiveSubscriptionPlansByType(type);
        return ResponseEntity.ok(activePlansByType);
    }

    /**
     * Récupère les plans d'abonnement avec un prix inférieur ou égal à un montant donné.
     */
    @GetMapping("/price")
    public ResponseEntity<List<SubscriptionPlanDto>> getSubscriptionPlansByMaxPrice(@RequestParam Double maxPrice) {
        List<SubscriptionPlanDto> plansByPrice = subscriptionPlanService.getSubscriptionPlansByMaxPrice(maxPrice);
        return ResponseEntity.ok(plansByPrice);
    }

    /**
     * Met à jour un plan d'abonnement existant.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SubscriptionPlanDto> updateSubscriptionPlan(@PathVariable Long id, 
                                                                   @Valid @RequestBody SubscriptionPlanDto subscriptionPlanDto) {
        SubscriptionPlanDto updatedPlan = subscriptionPlanService.updateSubscriptionPlan(id, subscriptionPlanDto);
        return ResponseEntity.ok(updatedPlan);
    }

    /**
     * Active ou désactive un plan d'abonnement.
     */
    @PatchMapping("/{id}/active")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SubscriptionPlanDto> setSubscriptionPlanActive(@PathVariable Long id, 
                                                                      @RequestParam boolean active) {
        SubscriptionPlanDto updatedPlan = subscriptionPlanService.setSubscriptionPlanActive(id, active);
        return ResponseEntity.ok(updatedPlan);
    }

    /**
     * Supprime un plan d'abonnement.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteSubscriptionPlan(@PathVariable Long id) {
        boolean deleted = subscriptionPlanService.deleteSubscriptionPlan(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
} 
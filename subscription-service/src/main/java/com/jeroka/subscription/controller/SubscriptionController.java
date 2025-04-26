package com.jeroka.subscription.controller;

import com.jeroka.subscription.dto.SubscriptionDto;
import com.jeroka.subscription.model.SubscriptionType;
import com.jeroka.subscription.service.SubscriptionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Contrôleur REST pour la gestion des abonnements.
 */
@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @Autowired
    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    /**
     * Crée un nouvel abonnement.
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or #subscriptionDto.userId == authentication.principal.id")
    public ResponseEntity<SubscriptionDto> createSubscription(@Valid @RequestBody SubscriptionDto subscriptionDto) {
        SubscriptionDto createdSubscription = subscriptionService.createSubscription(subscriptionDto);
        return new ResponseEntity<>(createdSubscription, HttpStatus.CREATED);
    }

    /**
     * Récupère un abonnement par son ID.
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @subscriptionService.getSubscriptionById(#id).userId == authentication.principal.id")
    public ResponseEntity<SubscriptionDto> getSubscription(@PathVariable Long id) {
        SubscriptionDto subscription = subscriptionService.getSubscriptionById(id);
        return ResponseEntity.ok(subscription);
    }

    /**
     * Récupère tous les abonnements.
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<SubscriptionDto>> getAllSubscriptions() {
        List<SubscriptionDto> subscriptions = subscriptionService.getAllSubscriptions();
        return ResponseEntity.ok(subscriptions);
    }

    /**
     * Récupère les abonnements d'un utilisateur spécifique.
     */
    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('ADMIN') or #userId == authentication.principal.id")
    public ResponseEntity<List<SubscriptionDto>> getSubscriptionsByUserId(@PathVariable String userId) {
        List<SubscriptionDto> subscriptions = subscriptionService.getSubscriptionsByUserId(userId);
        return ResponseEntity.ok(subscriptions);
    }

    /**
     * Récupère l'abonnement actif d'un utilisateur.
     */
    @GetMapping("/user/{userId}/active")
    @PreAuthorize("hasRole('ADMIN') or #userId == authentication.principal.id")
    public ResponseEntity<SubscriptionDto> getActiveSubscriptionByUserId(@PathVariable String userId) {
        SubscriptionDto subscription = subscriptionService.getActiveSubscriptionByUserId(userId);
        if (subscription == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(subscription);
    }

    /**
     * Met à jour un abonnement existant.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @subscriptionService.getSubscriptionById(#id).userId == authentication.principal.id")
    public ResponseEntity<SubscriptionDto> updateSubscription(@PathVariable Long id, 
                                                           @Valid @RequestBody SubscriptionDto subscriptionDto) {
        SubscriptionDto updatedSubscription = subscriptionService.updateSubscription(id, subscriptionDto);
        return ResponseEntity.ok(updatedSubscription);
    }

    /**
     * Active ou désactive un abonnement.
     */
    @PatchMapping("/{id}/active")
    @PreAuthorize("hasRole('ADMIN') or @subscriptionService.getSubscriptionById(#id).userId == authentication.principal.id")
    public ResponseEntity<SubscriptionDto> setSubscriptionActive(@PathVariable Long id, 
                                                              @RequestParam boolean active) {
        SubscriptionDto updatedSubscription = subscriptionService.setSubscriptionActive(id, active);
        return ResponseEntity.ok(updatedSubscription);
    }

    /**
     * Active ou désactive le renouvellement automatique d'un abonnement.
     */
    @PatchMapping("/{id}/auto-renew")
    @PreAuthorize("hasRole('ADMIN') or @subscriptionService.getSubscriptionById(#id).userId == authentication.principal.id")
    public ResponseEntity<SubscriptionDto> setAutoRenew(@PathVariable Long id, 
                                                     @RequestParam boolean autoRenew) {
        SubscriptionDto updatedSubscription = subscriptionService.setAutoRenew(id, autoRenew);
        return ResponseEntity.ok(updatedSubscription);
    }

    /**
     * Supprime un abonnement.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteSubscription(@PathVariable Long id) {
        boolean deleted = subscriptionService.deleteSubscription(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Récupère les abonnements par type.
     */
    @GetMapping("/type/{type}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<SubscriptionDto>> getSubscriptionsByType(@PathVariable SubscriptionType type) {
        List<SubscriptionDto> subscriptions = subscriptionService.getSubscriptionsByType(type);
        return ResponseEntity.ok(subscriptions);
    }

    /**
     * Récupère les abonnements qui expirent avant une date donnée.
     */
    @GetMapping("/expiring")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<SubscriptionDto>> getSubscriptionsExpiringBefore(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date) {
        List<SubscriptionDto> subscriptions = subscriptionService.getSubscriptionsExpiringBefore(date);
        return ResponseEntity.ok(subscriptions);
    }

    /**
     * Renouvelle automatiquement les abonnements qui expirent bientôt.
     */
    @PostMapping("/renew")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Integer> renewSubscriptions() {
        int renewedCount = subscriptionService.renewSubscriptions();
        return ResponseEntity.ok(renewedCount);
    }
} 
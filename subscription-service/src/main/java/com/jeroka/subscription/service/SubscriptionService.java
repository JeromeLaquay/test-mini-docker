package com.jeroka.subscription.service;

import com.jeroka.subscription.dto.SubscriptionDto;
import com.jeroka.subscription.model.SubscriptionType;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service pour la gestion des abonnements.
 */
public interface SubscriptionService {

    /**
     * Crée un nouvel abonnement.
     *
     * @param subscriptionDto DTO de l'abonnement à créer
     * @return DTO de l'abonnement créé
     */
    SubscriptionDto createSubscription(SubscriptionDto subscriptionDto);

    /**
     * Récupère un abonnement par son ID.
     *
     * @param id ID de l'abonnement
     * @return DTO de l'abonnement trouvé
     */
    SubscriptionDto getSubscriptionById(Long id);

    /**
     * Récupère tous les abonnements.
     *
     * @return Liste des DTOs d'abonnements
     */
    List<SubscriptionDto> getAllSubscriptions();

    /**
     * Récupère les abonnements d'un utilisateur spécifique.
     *
     * @param userId ID de l'utilisateur
     * @return Liste des DTOs d'abonnements de l'utilisateur
     */
    List<SubscriptionDto> getSubscriptionsByUserId(String userId);

    /**
     * Récupère l'abonnement actif d'un utilisateur.
     *
     * @param userId ID de l'utilisateur
     * @return DTO de l'abonnement actif ou null
     */
    SubscriptionDto getActiveSubscriptionByUserId(String userId);

    /**
     * Met à jour un abonnement existant.
     *
     * @param id ID de l'abonnement à mettre à jour
     * @param subscriptionDto DTO avec les nouvelles valeurs
     * @return DTO de l'abonnement mis à jour
     */
    SubscriptionDto updateSubscription(Long id, SubscriptionDto subscriptionDto);

    /**
     * Active ou désactive un abonnement.
     *
     * @param id ID de l'abonnement
     * @param active Nouvel état d'activation
     * @return DTO de l'abonnement mis à jour
     */
    SubscriptionDto setSubscriptionActive(Long id, boolean active);

    /**
     * Active ou désactive le renouvellement automatique d'un abonnement.
     *
     * @param id ID de l'abonnement
     * @param autoRenew Nouvel état de renouvellement automatique
     * @return DTO de l'abonnement mis à jour
     */
    SubscriptionDto setAutoRenew(Long id, boolean autoRenew);

    /**
     * Supprime un abonnement.
     *
     * @param id ID de l'abonnement à supprimer
     * @return true si supprimé avec succès
     */
    boolean deleteSubscription(Long id);

    /**
     * Récupère les abonnements par type.
     *
     * @param type Type d'abonnement
     * @return Liste des DTOs d'abonnements du type spécifié
     */
    List<SubscriptionDto> getSubscriptionsByType(SubscriptionType type);

    /**
     * Récupère les abonnements qui expirent avant une date donnée.
     *
     * @param date Date d'expiration maximale
     * @return Liste des DTOs d'abonnements qui expirent avant la date spécifiée
     */
    List<SubscriptionDto> getSubscriptionsExpiringBefore(LocalDateTime date);

    /**
     * Renouvelle automatiquement les abonnements qui expirent bientôt et sont configurés pour le renouvellement automatique.
     *
     * @return Nombre d'abonnements renouvelés
     */
    int renewSubscriptions();
} 
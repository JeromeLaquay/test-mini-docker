package com.jeroka.subscription.service;

import com.jeroka.subscription.dto.SubscriptionPlanDto;
import com.jeroka.subscription.model.SubscriptionType;

import java.util.List;

/**
 * Service pour la gestion des plans d'abonnement.
 */
public interface SubscriptionPlanService {

    /**
     * Crée un nouveau plan d'abonnement.
     *
     * @param subscriptionPlanDto DTO du plan à créer
     * @return DTO du plan créé
     */
    SubscriptionPlanDto createSubscriptionPlan(SubscriptionPlanDto subscriptionPlanDto);

    /**
     * Récupère un plan d'abonnement par son ID.
     *
     * @param id ID du plan
     * @return DTO du plan trouvé
     */
    SubscriptionPlanDto getSubscriptionPlanById(Long id);

    /**
     * Récupère un plan d'abonnement par son nom.
     *
     * @param name Nom du plan
     * @return DTO du plan trouvé
     */
    SubscriptionPlanDto getSubscriptionPlanByName(String name);

    /**
     * Récupère tous les plans d'abonnement.
     *
     * @return Liste des DTOs de plans
     */
    List<SubscriptionPlanDto> getAllSubscriptionPlans();

    /**
     * Récupère tous les plans d'abonnement actifs.
     *
     * @return Liste des DTOs de plans actifs
     */
    List<SubscriptionPlanDto> getActiveSubscriptionPlans();

    /**
     * Récupère les plans d'abonnement par type.
     *
     * @param type Type de plan
     * @return Liste des DTOs de plans du type spécifié
     */
    List<SubscriptionPlanDto> getSubscriptionPlansByType(SubscriptionType type);

    /**
     * Récupère les plans d'abonnement actifs par type.
     *
     * @param type Type de plan
     * @return Liste des DTOs de plans actifs du type spécifié
     */
    List<SubscriptionPlanDto> getActiveSubscriptionPlansByType(SubscriptionType type);

    /**
     * Met à jour un plan d'abonnement existant.
     *
     * @param id ID du plan à mettre à jour
     * @param subscriptionPlanDto DTO avec les nouvelles valeurs
     * @return DTO du plan mis à jour
     */
    SubscriptionPlanDto updateSubscriptionPlan(Long id, SubscriptionPlanDto subscriptionPlanDto);

    /**
     * Active ou désactive un plan d'abonnement.
     *
     * @param id ID du plan
     * @param active Nouvel état d'activation
     * @return DTO du plan mis à jour
     */
    SubscriptionPlanDto setSubscriptionPlanActive(Long id, boolean active);

    /**
     * Supprime un plan d'abonnement.
     *
     * @param id ID du plan à supprimer
     * @return true si supprimé avec succès
     */
    boolean deleteSubscriptionPlan(Long id);

    /**
     * Récupère les plans d'abonnement avec un prix inférieur ou égal à un montant donné.
     *
     * @param maxPrice Prix maximum
     * @return Liste des DTOs de plans avec un prix inférieur ou égal
     */
    List<SubscriptionPlanDto> getSubscriptionPlansByMaxPrice(Double maxPrice);
} 
package com.jeroka.subscription.repository;

import com.jeroka.subscription.model.SubscriptionPlan;
import com.jeroka.subscription.model.SubscriptionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository pour l'entité SubscriptionPlan.
 */
@Repository
public interface SubscriptionPlanRepository extends JpaRepository<SubscriptionPlan, Long> {

    /**
     * Trouve un plan d'abonnement par son nom.
     *
     * @param name Nom du plan
     * @return Le plan d'abonnement ou Optional.empty()
     */
    Optional<SubscriptionPlan> findByName(String name);

    /**
     * Trouve tous les plans d'abonnement actifs.
     *
     * @return Liste des plans d'abonnement actifs
     */
    List<SubscriptionPlan> findByIsActiveTrue();

    /**
     * Trouve tous les plans d'abonnement d'un type spécifique.
     *
     * @param type Type de plan
     * @return Liste des plans d'abonnement du type spécifié
     */
    List<SubscriptionPlan> findByType(SubscriptionType type);

    /**
     * Trouve tous les plans d'abonnement d'un type spécifique qui sont actifs.
     *
     * @param type Type de plan
     * @return Liste des plans d'abonnement actifs du type spécifié
     */
    List<SubscriptionPlan> findByTypeAndIsActiveTrue(SubscriptionType type);

    /**
     * Trouve les plans d'abonnement avec un prix inférieur ou égal à un montant donné.
     *
     * @param price Prix maximum
     * @return Liste des plans d'abonnement avec un prix inférieur ou égal
     */
    List<SubscriptionPlan> findByPriceLessThanEqual(Double price);
} 
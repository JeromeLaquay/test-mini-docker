package com.jeroka.subscription.repository;

import com.jeroka.subscription.model.Subscription;
import com.jeroka.subscription.model.SubscriptionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository pour l'entité Subscription.
 */
@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    /**
     * Trouve tous les abonnements d'un utilisateur spécifique.
     *
     * @param userId ID de l'utilisateur
     * @return Liste des abonnements
     */
    List<Subscription> findByUserId(String userId);

    /**
     * Trouve l'abonnement actif d'un utilisateur.
     *
     * @param userId ID de l'utilisateur
     * @param isActive Statut d'activation
     * @return L'abonnement actif ou Optional.empty()
     */
    Optional<Subscription> findByUserIdAndIsActive(String userId, boolean isActive);

    /**
     * Trouve tous les abonnements d'un type spécifique.
     *
     * @param type Type d'abonnement
     * @return Liste des abonnements du type spécifié
     */
    List<Subscription> findByType(SubscriptionType type);

    /**
     * Trouve tous les abonnements qui expirent avant une date donnée.
     *
     * @param date Date d'expiration maximale
     * @return Liste des abonnements qui expirent avant la date spécifiée
     */
    List<Subscription> findByEndDateBeforeAndIsActive(LocalDateTime date, boolean isActive);

    /**
     * Compte le nombre d'abonnements par type.
     *
     * @return Liste des comptages par type d'abonnement
     */
    @Query("SELECT s.type, COUNT(s) FROM Subscription s GROUP BY s.type")
    List<Object[]> countByType();
    
    /**
     * Trouve tous les abonnements qui doivent être renouvelés.
     *
     * @param date Date de fin maximale pour le renouvellement
     * @return Liste des abonnements à renouveler
     */
    @Query("SELECT s FROM Subscription s WHERE s.endDate < :date AND s.isActive = true AND s.autoRenew = true")
    List<Subscription> findSubscriptionsToRenew(@Param("date") LocalDateTime date);
} 
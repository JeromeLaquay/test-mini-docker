package com.jeroka.auth.repository;

import com.jeroka.auth.model.Abonnement;
import com.jeroka.auth.model.User;
import com.jeroka.auth.model.enums.StatutAbonnement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AbonnementRepository extends JpaRepository<Abonnement, Long> {
    List<Abonnement> findByUser(User user);
    List<Abonnement> findByUserAndStatut(User user, StatutAbonnement statut);
    List<Abonnement> findByDateFinBefore(LocalDateTime date);
    List<Abonnement> findByDateProchaineFacturationBefore(LocalDateTime date);
} 
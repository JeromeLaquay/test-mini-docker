package com.jeroka.auth.repository;

import com.jeroka.auth.model.Facture;
import com.jeroka.auth.model.User;
import com.jeroka.auth.model.enums.StatutFacture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface FactureRepository extends JpaRepository<Facture, Long> {
    List<Facture> findByUser(User user);
    List<Facture> findByUserAndStatut(User user, StatutFacture statut);
    Optional<Facture> findByNumero(String numero);
    List<Facture> findByDateEmissionBetween(LocalDate debut, LocalDate fin);
} 
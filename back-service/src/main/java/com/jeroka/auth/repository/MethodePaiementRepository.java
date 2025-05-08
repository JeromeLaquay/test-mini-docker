package com.jeroka.auth.repository;

import com.jeroka.auth.model.MethodePaiement;
import com.jeroka.auth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MethodePaiementRepository extends JpaRepository<MethodePaiement, Long> {
    List<MethodePaiement> findByUser(User user);
    Optional<MethodePaiement> findByUserAndParDefautTrue(User user);
    Optional<MethodePaiement> findByUserAndToken(User user, String token);
} 
package com.jeroka.auth.repository;

import com.jeroka.auth.model.HistoriqueActivite;
import com.jeroka.auth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface HistoriqueActiviteRepository extends JpaRepository<HistoriqueActivite, Long> {
    List<HistoriqueActivite> findByUser(User user);
    List<HistoriqueActivite> findByTypeEntite(String typeEntite);
    List<HistoriqueActivite> findByDateActionBetween(LocalDateTime debut, LocalDateTime fin);
    List<HistoriqueActivite> findByUserAndTypeEntite(User user, String typeEntite);
} 
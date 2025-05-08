package com.jeroka.auth.repository;

import com.jeroka.auth.model.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Long> {
    List<Plan> findByActifTrue();
    Optional<Plan> findByNom(String nom);
} 
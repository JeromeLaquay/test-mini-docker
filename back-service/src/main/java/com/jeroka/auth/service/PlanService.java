package com.jeroka.auth.service;

import com.jeroka.auth.dto.PlanRequestDTO;
import com.jeroka.auth.mapper.PlanMapper;
import com.jeroka.auth.model.Plan;
import com.jeroka.auth.repository.PlanRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@Validated
public class PlanService {
    @Autowired
    private PlanRepository planRepository;

    public List<Plan> getAllPlans() {
        return planRepository.findAll();
    }

    public List<Plan> getActivePlans() {
        return planRepository.findByActifTrue();
    }

    public Plan getPlanById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("L'ID du plan ne peut pas être null");
        }
        return planRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Plan non trouvé avec l'ID : " + id));
    }

    public Plan createPlan(@Valid PlanRequestDTO requestDTO) {
        if (requestDTO == null) {
            throw new IllegalArgumentException("La requête de plan ne peut pas être null");
        }

        Plan plan = PlanMapper.toEntity(requestDTO);
        plan.setDateCreation(LocalDateTime.now());
        plan.setDateMiseAJour(LocalDateTime.now());

        return planRepository.save(plan);
    }

    public Plan updatePlan(Long id, @Valid PlanRequestDTO requestDTO) {
        if (id == null) {
            throw new IllegalArgumentException("L'ID du plan ne peut pas être null");
        }
        if (requestDTO == null) {
            throw new IllegalArgumentException("La requête de mise à jour ne peut pas être null");
        }

        Plan plan = planRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Plan non trouvé avec l'ID : " + id));
        
        plan.setNom(requestDTO.getNom());
        plan.setDescription(requestDTO.getDescription());
        plan.setPrixMensuel(requestDTO.getPrixMensuel());
        plan.setPrixAnnuel(requestDTO.getPrixAnnuel());
        plan.setPeriodeEssaiJours(requestDTO.getPeriodeEssaiJours());
        plan.setActif(requestDTO.getActif());
        plan.setDateMiseAJour(LocalDateTime.now());

        return planRepository.save(plan);
    }

    public void deletePlan(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("L'ID du plan ne peut pas être null");
        }
        if (!planRepository.existsById(id)) {
            throw new EntityNotFoundException("Plan non trouvé avec l'ID : " + id);
        }
        planRepository.deleteById(id);
    }
} 
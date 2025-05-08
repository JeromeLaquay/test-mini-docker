package com.jeroka.auth.mapper;

import com.jeroka.auth.dto.PlanRequestDTO;
import com.jeroka.auth.dto.PlanResponseDTO;
import com.jeroka.auth.model.Plan;
import java.util.List;
import java.util.Collections;
import java.util.stream.Collectors;

public interface PlanMapper {
    
    public static Plan toEntity(PlanRequestDTO dto) {
        Plan plan = new Plan(
            dto.getNom(),
            dto.getPrixMensuel()
        );
        
        plan.setDescription(dto.getDescription());
        plan.setPrixAnnuel(dto.getPrixAnnuel());
        plan.setPeriodeEssaiJours(dto.getPeriodeEssaiJours());
        plan.setActif(dto.getActif());
        
        return plan;
    }
    
    public static PlanResponseDTO toDTO(Plan plan) {
        if (plan == null) {
            return null;
        }
        
        PlanResponseDTO dto = new PlanResponseDTO(
            plan.getId(),
            plan.getNom(),
            plan.getPrixMensuel(),
            plan.getActif(),
            plan.getDateCreation()
        );
        
        dto.setDescription(plan.getDescription());
        dto.setPrixAnnuel(plan.getPrixAnnuel());
        dto.setPeriodeEssaiJours(plan.getPeriodeEssaiJours());
        dto.setDateMiseAJour(plan.getDateMiseAJour());
        
        return dto;
    }
    
    public static List<PlanResponseDTO> toDTOs(List<Plan> plans) {
        if (plans == null) {
            return Collections.emptyList();
        }
        return plans.stream()
                .map(PlanMapper::toDTO)
                .collect(Collectors.toList());
    }
} 
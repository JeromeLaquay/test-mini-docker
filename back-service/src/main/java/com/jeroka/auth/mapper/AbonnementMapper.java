package com.jeroka.auth.mapper;

import com.jeroka.auth.dto.AbonnementRequestDTO;
import com.jeroka.auth.dto.AbonnementResponseDTO;
import com.jeroka.auth.model.Abonnement;
import com.jeroka.auth.model.User;
import com.jeroka.auth.model.Plan;
import com.jeroka.auth.model.MethodePaiement;
import com.jeroka.auth.model.enums.StatutAbonnement;
import java.util.List;
import java.util.Collections;
import java.util.stream.Collectors;

public class AbonnementMapper {
    
    public static Abonnement toEntity(AbonnementRequestDTO dto, User user, Plan plan, 
                                    MethodePaiement methodePaiement) {
        Abonnement abonnement = new Abonnement(
            user,
            plan,
            methodePaiement,
            StatutAbonnement.EN_ATTENTE,
            dto.getPeriode(),
            dto.getDateDebut(),
            dto.getMontant()
        );
        
        abonnement.setDateFinEssai(dto.getDateFinEssai());
        abonnement.setAutoRenouvellement(dto.getAutoRenouvellement());
        
        return abonnement;
    }
    
    public static AbonnementResponseDTO toDTO(Abonnement abonnement) {
        if (abonnement == null) {
            return null;
        }
        
        AbonnementResponseDTO dto = new AbonnementResponseDTO(
            abonnement.getId(),
            abonnement.getUser().getId(),
            abonnement.getPlan().getId(),
            abonnement.getStatut(),
            abonnement.getPeriode(),
            abonnement.getDateDebut(),
            abonnement.getMontant(),
            abonnement.getAutoRenouvellement(),
            abonnement.getDateCreation()
        );
        
        if (abonnement.getMethodePaiement() != null) {
            dto.setMethodePaiementId(abonnement.getMethodePaiement().getId());
        }
        
        dto.setDateFinEssai(abonnement.getDateFinEssai());
        dto.setDateProchaineFacturation(abonnement.getDateProchaineFacturation());
        dto.setDateFin(abonnement.getDateFin());
        dto.setRaisonAnnulation(abonnement.getRaisonAnnulation());
        dto.setDateMiseAJour(abonnement.getDateMiseAJour());
        
        return dto;
    }
    
    public static List<AbonnementResponseDTO> toDTOs(List<Abonnement> abonnements) {
        if (abonnements == null) {
            return Collections.emptyList();
        }
        return abonnements.stream()
                .map(AbonnementMapper::toDTO)
                .collect(Collectors.toList());
    }
} 
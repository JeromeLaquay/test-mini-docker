package com.jeroka.auth.mapper;

import com.jeroka.auth.dto.FactureRequestDTO;
import com.jeroka.auth.dto.FactureResponseDTO;
import com.jeroka.auth.model.Facture;
import com.jeroka.auth.model.Transaction;
import com.jeroka.auth.model.User;
import com.jeroka.auth.model.enums.StatutFacture;
import java.util.List;
import java.util.Collections;
import java.util.stream.Collectors;
import java.time.LocalDateTime;

public class FactureMapper {
    
    public static Facture toEntity(FactureRequestDTO dto, Transaction transaction, User user) {
        if (dto == null) {
            return null;
        }
        
        Facture facture = new Facture(
            transaction,
            user,
            dto.getNumero(),
            dto.getDateEmission(),
            dto.getMontantHT(),
            dto.getTauxTVA()
        );
        
        facture.setDatePaiement(dto.getDatePaiement());
        facture.setStatut(StatutFacture.EN_ATTENTE);
        facture.setUrlPDF(dto.getUrlPDF());
        facture.setNotes(dto.getNotes());
        
        return facture;
    }
    
    public static FactureResponseDTO toDTO(Facture facture) {
        if (facture == null) {
            return null;
        }
        
        FactureResponseDTO dto = new FactureResponseDTO(
            facture.getId(),
            facture.getTransaction().getId(),
            facture.getUser().getId(),
            facture.getNumero(),
            facture.getDateEmission(),
            facture.getMontantHT(),
            facture.getTauxTVA(),
            facture.getMontantTVA(),
            facture.getMontantTTC(),
            facture.getStatut()
        );
        
        dto.setDatePaiement(facture.getDatePaiement());
        dto.setUrlPDF(facture.getUrlPDF());
        dto.setNotes(facture.getNotes());
        dto.setDateCreation(facture.getDateCreation());
        
        return dto;
    }
    
    public static List<FactureResponseDTO> toDTOs(List<Facture> factures) {
        if (factures == null) {
            return Collections.emptyList();
        }
        return factures.stream()
                .map(FactureMapper::toDTO)
                .collect(Collectors.toList());
    }
} 
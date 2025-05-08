package com.jeroka.auth.mapper;

import com.jeroka.auth.dto.MethodePaiementRequestDTO;
import com.jeroka.auth.dto.MethodePaiementResponseDTO;
import com.jeroka.auth.model.MethodePaiement;
import com.jeroka.auth.model.User;
import java.util.List;
import java.util.Collections;
import java.util.stream.Collectors;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MethodePaiementMapper {
    
    // Définir un formateur de date pour MM/YY
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MM/yy");

    public static MethodePaiement toEntity(MethodePaiementRequestDTO dto, User user) {
        if (dto == null) {
            return null;
        }

        MethodePaiement methodePaiement = new MethodePaiement();
        methodePaiement.setType(dto.getType());
        methodePaiement.setMarque(dto.getMarque());
        methodePaiement.setDerniersChiffres(dto.getDerniersChiffres());
        
        // Conversion de String MM/YY en LocalDate
        if (dto.getDateExpiration() != null) {
            try {
                String[] parts = dto.getDateExpiration().split("/");
                if (parts.length == 2) {
                    int month = Integer.parseInt(parts[0]);
                    int year = 2000 + Integer.parseInt(parts[1]);
                    LocalDate expiryDate = LocalDate.of(year, month, 1);
                    methodePaiement.setDateExpiration(expiryDate);
                }
            } catch (Exception e) {
                // Log l'erreur ou gérer l'exception selon vos besoins
                throw new IllegalArgumentException("Format de date invalide. Utilisez le format MM/YY");
            }
        }
        
        methodePaiement.setNomTitulaire(dto.getNomTitulaire());
        methodePaiement.setUser(user);
        methodePaiement.setIdentifiantExterne(dto.getIdentifiantExterne());
        methodePaiement.setParDefaut(dto.getParDefaut());
        
        return methodePaiement;
    }
    
    public static MethodePaiementResponseDTO toDTO(MethodePaiement entity) {
        if (entity == null) {
            return null;
        }

        MethodePaiementResponseDTO dto = new MethodePaiementResponseDTO();
        dto.setId(entity.getId());
        dto.setType(entity.getType());
        dto.setMarque(entity.getMarque());
        dto.setDerniersChiffres(entity.getDerniersChiffres());
        
        // Conversion de LocalDate en String au format MM/YY
        if (entity.getDateExpiration() != null) {
            String formattedDate = entity.getDateExpiration().format(DATE_FORMATTER);
            dto.setDateExpiration(formattedDate);
        }
        
        dto.setNomTitulaire(entity.getNomTitulaire());
        dto.setUserId(entity.getUser().getId());
        dto.setIdentifiantExterne(entity.getIdentifiantExterne());
        dto.setParDefaut(entity.getParDefaut());
        dto.setDateCreation(entity.getDateCreation());
        dto.setDateMiseAJour(entity.getDateMiseAJour());
        
        return dto;
    }
    
    public static List<MethodePaiementResponseDTO> toDTOs(List<MethodePaiement> methodesPaiement) {
        if (methodesPaiement == null) {
            return Collections.emptyList();
        }
        return methodesPaiement.stream()
                .map(MethodePaiementMapper::toDTO)
                .collect(Collectors.toList());
    }
} 
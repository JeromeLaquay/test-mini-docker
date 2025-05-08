package com.jeroka.auth.mapper;

import com.jeroka.auth.dto.HistoriqueActiviteRequestDTO;
import com.jeroka.auth.dto.HistoriqueActiviteResponseDTO;
import com.jeroka.auth.model.HistoriqueActivite;
import com.jeroka.auth.model.User;
import java.util.List;
import java.util.Collections;
import java.util.stream.Collectors;

public class HistoriqueActiviteMapper {
    
    public static HistoriqueActivite toEntity(HistoriqueActiviteRequestDTO dto, User user) {
        HistoriqueActivite historique = new HistoriqueActivite(
            user,
            dto.getTypeEntite(),
            dto.getIdEntite(),
            dto.getAction()
        );
        
        historique.setDetails(dto.getDetails());
        historique.setAdresseIP(dto.getAdresseIP());
        historique.setUserAgent(dto.getUserAgent());
        
        return historique;
    }
    
    public static HistoriqueActiviteResponseDTO toDTO(HistoriqueActivite historique) {
        if (historique == null) {
            return null;
        }
        
        HistoriqueActiviteResponseDTO dto = new HistoriqueActiviteResponseDTO(
            historique.getId(),
            historique.getUser().getId(),
            historique.getTypeEntite(),
            historique.getIdEntite(),
            historique.getAction(),
            historique.getDateAction()
        );
        
        dto.setDetails(historique.getDetails());
        dto.setAdresseIP(historique.getAdresseIP());
        dto.setUserAgent(historique.getUserAgent());
        
        return dto;
    }
    
    public static List<HistoriqueActiviteResponseDTO> toDTOs(List<HistoriqueActivite> historiques) {
        if (historiques == null) {
            return Collections.emptyList();
        }
        return historiques.stream()
                .map(HistoriqueActiviteMapper::toDTO)
                .collect(Collectors.toList());
    }
} 
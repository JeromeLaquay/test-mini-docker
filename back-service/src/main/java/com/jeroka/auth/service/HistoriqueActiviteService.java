package com.jeroka.auth.service;

import com.jeroka.auth.dto.HistoriqueActiviteRequestDTO;
import com.jeroka.auth.mapper.HistoriqueActiviteMapper;
import com.jeroka.auth.model.HistoriqueActivite;
import com.jeroka.auth.model.User;
import com.jeroka.auth.repository.HistoriqueActiviteRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import java.time.LocalDateTime;
import java.util.List;
import com.jeroka.auth.exception.ResourceNotFoundException;

@Service
@Transactional
@Validated
public class HistoriqueActiviteService {
    @Autowired
    private HistoriqueActiviteRepository historiqueActiviteRepository;
    @Autowired
    private UserService userService;

    public HistoriqueActivite logActivity(@Valid HistoriqueActiviteRequestDTO requestDTO) {
        if (requestDTO == null) {
            throw new IllegalArgumentException("La requête d'historique d'activité ne peut pas être null");
        }

        User user = userService.getUserById(requestDTO.getUserId());
        if (user == null) {
            throw new EntityNotFoundException("Utilisateur non trouvé avec l'ID : " + requestDTO.getUserId());
        }

        HistoriqueActivite activite = HistoriqueActiviteMapper.toEntity(requestDTO, user);
        activite.setDateAction(LocalDateTime.now());
        
        return historiqueActiviteRepository.save(activite);
    }

    public List<HistoriqueActivite> getActivitiesByUser(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("L'ID de l'utilisateur ne peut pas être null");
        }

        User user = userService.getUserById(userId);
        if (user == null) {
            throw new EntityNotFoundException("Utilisateur non trouvé avec l'ID : " + userId);
        }

        return historiqueActiviteRepository.findByUser(user);
    }

    public List<HistoriqueActivite> getActivitiesByType(String typeEntite) {
        if (typeEntite == null || typeEntite.trim().isEmpty()) {
            throw new IllegalArgumentException("Le type d'entité ne peut pas être null ou vide");
        }

        return historiqueActiviteRepository.findByTypeEntite(typeEntite);
    }

    public List<HistoriqueActivite> getActivitiesByPeriod(LocalDateTime debut, LocalDateTime fin) {
        if (debut == null || fin == null) {
            throw new IllegalArgumentException("Les dates de début et de fin ne peuvent pas être null");
        }
        if (debut.isAfter(fin)) {
            throw new IllegalArgumentException("La date de début doit être antérieure à la date de fin");
        }

        return historiqueActiviteRepository.findByDateActionBetween(debut, fin);
    }

    public HistoriqueActivite getHistoriqueById(Long id) {
        return historiqueActiviteRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Historique d'activité non trouvé avec l'id : " + id));
    }
} 
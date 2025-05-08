package com.jeroka.auth.mapper;

import com.jeroka.auth.dto.UserRequestDTO;
import com.jeroka.auth.dto.UserResponseDTO;
import com.jeroka.auth.model.User;
import com.jeroka.auth.model.Role;
import com.jeroka.auth.model.enums.StatutUtilisateur;
import java.util.List;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.HashSet;
import java.time.LocalDateTime;

public class UserMapper {
    
    public static User toEntity(UserRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        
        User user = new User(
            dto.getUsername(),
            dto.getEmail(),
            dto.getPassword()
        );
        
        user.setNom(dto.getNom());
        user.setPrenom(dto.getPrenom());
        user.setTelephone(dto.getTelephone());
        user.setAdresse(dto.getAdresse());
        user.setVille(dto.getVille());
        user.setCodePostal(dto.getCodePostal());
        user.setPays(dto.getPays());
        user.setDateCreation(LocalDateTime.now());
        
        // Les rôles seront gérés séparément par le service
        
        return user;
    }
    
    public static UserResponseDTO toDTO(User user) {
        if (user == null) {
            return null;
        }
        
        UserResponseDTO dto = new UserResponseDTO(
            user.getId(),
            user.getUsername(),
            user.getEmail(),
            user.getNom(),
            user.getPrenom()
        );
        
        dto.setTelephone(user.getTelephone());
        dto.setAdresse(user.getAdresse());
        dto.setVille(user.getVille());
        dto.setCodePostal(user.getCodePostal());
        dto.setPays(user.getPays());
        
        // Gestion correcte des rôles
        dto.setRoles(user.getRoles() != null ? new HashSet<>(user.getRoles()) : new HashSet<>());
        
        // Conversion des listes d'IDs
        if (user.getMethodesPaiement() != null) {
            dto.setMethodesPaiement(user.getMethodesPaiement().stream()
                .map(mp -> mp.getId())
                .collect(Collectors.toList()));
        }
        
        if (user.getAbonnements() != null) {
            dto.setAbonnements(user.getAbonnements().stream()
                .map(ab -> ab.getId())
                .collect(Collectors.toList()));
        }
        
        dto.setDateCreation(user.getDateCreation());
        dto.setDateMiseAJour(user.getDateMiseAJour());
        
        return dto;
    }
    
    public static List<UserResponseDTO> toDTOs(List<User> users) {
        if (users == null) {
            return Collections.emptyList();
        }
        return users.stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    public static UserResponseDTO toDTOWithoutSensitiveData(User user) {
        UserResponseDTO dto = UserMapper.toDTO(user);
        if (dto != null) {
            dto.setAdresse(null);
            dto.setTelephone(null);
            dto.setCodePostal(null);
            dto.setMethodesPaiement(null);
        }
        return dto;
    }
} 
package com.jeroka.auth.controller;

import com.jeroka.auth.dto.HistoriqueActiviteRequestDTO;
import com.jeroka.auth.dto.HistoriqueActiviteResponseDTO;
import com.jeroka.auth.service.HistoriqueActiviteService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalDateTime;
import java.util.List;
import com.jeroka.auth.mapper.HistoriqueActiviteMapper;
import com.jeroka.auth.model.HistoriqueActivite;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/historique")
public class HistoriqueActiviteController {

    @Autowired
    private HistoriqueActiviteService historiqueActiviteService;

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<HistoriqueActiviteResponseDTO>> getActivitiesByUser(
            @PathVariable @NotNull(message = "L'ID de l'utilisateur ne peut pas être null") Long userId) {
        List<HistoriqueActivite> activities = historiqueActiviteService.getActivitiesByUser(userId);
        return ResponseEntity.ok(HistoriqueActiviteMapper.toDTOs(activities));
    }

    @GetMapping("/type/{typeEntite}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<HistoriqueActiviteResponseDTO>> getActivitiesByType(
            @PathVariable @NotBlank(message = "Le type d'entité ne peut pas être vide") String typeEntite) {
        List<HistoriqueActivite> activities = historiqueActiviteService.getActivitiesByType(typeEntite);
        return ResponseEntity.ok(HistoriqueActiviteMapper.toDTOs(activities));
    }

    @GetMapping("/periode")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<HistoriqueActiviteResponseDTO>> getActivitiesByPeriod(
            @RequestParam @NotNull(message = "La date de début ne peut pas être null") 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime debut,
            @RequestParam @NotNull(message = "La date de fin ne peut pas être null") 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin) {
        List<HistoriqueActivite> activities = historiqueActiviteService.getActivitiesByPeriod(debut, fin);
        return ResponseEntity.ok(HistoriqueActiviteMapper.toDTOs(activities));
    }

    @PostMapping("/log")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<HistoriqueActiviteResponseDTO> logActivity(
            @Valid @RequestBody HistoriqueActiviteRequestDTO requestDTO) {
        HistoriqueActivite createdActivity = historiqueActiviteService.logActivity(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(HistoriqueActiviteMapper.toDTO(createdActivity));
    }

    @GetMapping("/{id}")
    public ResponseEntity<HistoriqueActiviteResponseDTO> getHistoriqueById(@PathVariable Long id) {
        HistoriqueActivite historique = historiqueActiviteService.getHistoriqueById(id);
        return ResponseEntity.ok(HistoriqueActiviteMapper.toDTO(historique));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("Une erreur est survenue : " + e.getMessage());
    }
} 
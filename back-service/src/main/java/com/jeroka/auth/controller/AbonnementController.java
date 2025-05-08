package com.jeroka.auth.controller;

import com.jeroka.auth.dto.AbonnementRequestDTO;
import com.jeroka.auth.dto.AbonnementResponseDTO;
import com.jeroka.auth.service.AbonnementService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import com.jeroka.auth.mapper.AbonnementMapper;
import com.jeroka.auth.model.Abonnement;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/abonnements")
public class AbonnementController {
    @Autowired
    private AbonnementService abonnementService;

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<AbonnementResponseDTO>> getAbonnementsByUser(
            @PathVariable @NotNull(message = "L'ID de l'utilisateur ne peut pas être null") Long userId) {
        List<Abonnement> abonnements = abonnementService.getAbonnementsByUser(userId);
        return ResponseEntity.ok(AbonnementMapper.toDTOs(abonnements));
    }

    @GetMapping("/user/{userId}/actifs")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<AbonnementResponseDTO>> getAbonnementsActifsByUser(
            @PathVariable @NotNull(message = "L'ID de l'utilisateur ne peut pas être null") Long userId) {
        List<Abonnement> abonnements = abonnementService.getAbonnementsActifsByUser(userId);
        return ResponseEntity.ok(AbonnementMapper.toDTOs(abonnements));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<AbonnementResponseDTO> getAbonnementById(
            @PathVariable @NotNull(message = "L'ID de l'abonnement ne peut pas être null") Long id) {
        Abonnement abonnement = abonnementService.getAbonnementById(id);
        return ResponseEntity.ok(AbonnementMapper.toDTO(abonnement));
    }

    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<AbonnementResponseDTO> createAbonnement(
            @Valid @RequestBody AbonnementRequestDTO requestDTO) {
        Abonnement createdAbonnement = abonnementService.createAbonnement(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(AbonnementMapper.toDTO(createdAbonnement));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<AbonnementResponseDTO> updateAbonnement(
            @PathVariable @NotNull(message = "L'ID de l'abonnement ne peut pas être null") Long id,
            @Valid @RequestBody AbonnementRequestDTO requestDTO) {
        Abonnement updatedAbonnement = abonnementService.updateAbonnement(id, requestDTO);
        return ResponseEntity.ok(AbonnementMapper.toDTO(updatedAbonnement));
    }

    @PostMapping("/{id}/cancel")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Void> cancelAbonnement(
            @PathVariable @NotNull(message = "L'ID de l'abonnement ne peut pas être null") Long id,
            @RequestParam @NotBlank(message = "La raison d'annulation ne peut pas être vide") String raison) {
        abonnementService.cancelAbonnement(id, raison);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/expires")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<AbonnementResponseDTO>> getAbonnementsExpires() {
        List<Abonnement> abonnements = abonnementService.getAbonnementsExpires();
        return ResponseEntity.ok(AbonnementMapper.toDTOs(abonnements));
    }

    @GetMapping("/a-facturer")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<AbonnementResponseDTO>> getAbonnementsAFacturer() {
        List<Abonnement> abonnements = abonnementService.getAbonnementsAFacturer();
        return ResponseEntity.ok(AbonnementMapper.toDTOs(abonnements));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("Une erreur est survenue : " + e.getMessage());
    }
} 
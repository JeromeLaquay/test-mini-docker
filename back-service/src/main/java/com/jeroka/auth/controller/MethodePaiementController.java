package com.jeroka.auth.controller;

import com.jeroka.auth.dto.MethodePaiementRequestDTO;
import com.jeroka.auth.dto.MethodePaiementResponseDTO;
import com.jeroka.auth.service.MethodePaiementService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.stream.Collectors;
import com.jeroka.auth.mapper.MethodePaiementMapper;
import com.jeroka.auth.model.MethodePaiement;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/methodes-paiement")
public class MethodePaiementController {
    @Autowired
    private MethodePaiementService methodePaiementService;

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<MethodePaiementResponseDTO>> getMethodesPaiementByUser(
            @PathVariable @NotNull(message = "L'ID de l'utilisateur ne peut pas être null") Long userId) {
        List<MethodePaiement> methodesPaiement = methodePaiementService.getMethodesPaiementByUser(userId);
        return ResponseEntity.ok(MethodePaiementMapper.toDTOs(methodesPaiement));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<MethodePaiementResponseDTO> getMethodePaiementById(
            @PathVariable @NotNull(message = "L'ID de la méthode de paiement ne peut pas être null") Long id) {
        MethodePaiement methodePaiement = methodePaiementService.getMethodePaiementById(id);
        return ResponseEntity.ok(MethodePaiementMapper.toDTO(methodePaiement));
    }

    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<MethodePaiementResponseDTO> createMethodePaiement(
            @Valid @RequestBody MethodePaiementRequestDTO requestDTO) {
        MethodePaiement createdMethodePaiement = methodePaiementService.createMethodePaiement(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(MethodePaiementMapper.toDTO(createdMethodePaiement));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<MethodePaiementResponseDTO> updateMethodePaiement(
            @PathVariable @NotNull(message = "L'ID de la méthode de paiement ne peut pas être null") Long id,
            @Valid @RequestBody MethodePaiementRequestDTO requestDTO) {
        MethodePaiement updatedMethodePaiement = methodePaiementService.updateMethodePaiement(id, requestDTO);
        return ResponseEntity.ok(MethodePaiementMapper.toDTO(updatedMethodePaiement));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Void> deleteMethodePaiement(
            @PathVariable @NotNull(message = "L'ID de la méthode de paiement ne peut pas être null") Long id) {
        methodePaiementService.deleteMethodePaiement(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("Une erreur est survenue : " + e.getMessage());
    }

    @PostMapping("/initialize")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<MethodePaiementResponseDTO> initializePayment(
            @Valid @RequestBody PaymentRequestDTO paymentData) {
        // Initialiser le paiement pour obtenir un token et créer la méthode de paiement
        return ResponseEntity.ok(methodePaiementService.initializePayment(paymentData));
    }
} 

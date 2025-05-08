package com.jeroka.auth.controller;

import com.jeroka.auth.model.Facture;
import com.jeroka.auth.service.FactureService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.jeroka.auth.dto.FactureResponseDTO;
import com.jeroka.auth.mapper.FactureMapper;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/factures")
public class FactureController {
    @Autowired
    private FactureService factureService;

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<FactureResponseDTO>> getFacturesByUser(@PathVariable Long userId) {
        List<Facture> factures = factureService.getFacturesByUser(userId);
        return ResponseEntity.ok(FactureMapper.toDTOs(factures));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<FactureResponseDTO> getFactureById(@PathVariable Long id) {
        Facture facture = factureService.getFactureById(id);
        return ResponseEntity.ok(FactureMapper.toDTO(facture));
    }

    @GetMapping("/numero/{numero}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<FactureResponseDTO> getFactureByNumero(@PathVariable String numero) {
        Facture facture = factureService.getFactureByNumero(numero);
        return ResponseEntity.ok(FactureMapper.toDTO(facture));
    }

    @PostMapping("/user/{userId}/transaction/{transactionId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<FactureResponseDTO> createFacture(
            @PathVariable Long userId,
            @PathVariable Long transactionId,
            @Valid @RequestBody Facture facture) {
        Facture createdFacture = factureService.createFacture(userId, transactionId, facture);
        return ResponseEntity.status(HttpStatus.CREATED).body(FactureMapper.toDTO(createdFacture));
    }

    @GetMapping("/periode")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<FactureResponseDTO>> getFacturesByPeriode(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate debut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {
        List<Facture> factures = factureService.getFacturesByPeriode(debut, fin);
        return ResponseEntity.ok(FactureMapper.toDTOs(factures));
    }
} 
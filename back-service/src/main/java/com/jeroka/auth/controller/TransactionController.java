package com.jeroka.auth.controller;

import com.jeroka.auth.dto.TransactionRequestDTO;
import com.jeroka.auth.dto.TransactionResponseDTO;
import com.jeroka.auth.service.TransactionService;
import jakarta.validation.Valid;
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
import com.jeroka.auth.mapper.TransactionMapper;
import com.jeroka.auth.model.Transaction;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<TransactionResponseDTO>> getTransactionsByUser(
            @PathVariable @NotNull(message = "L'ID de l'utilisateur ne peut pas être null") Long userId) {
        List<Transaction> transactions = transactionService.getTransactionsByUser(userId);
        return ResponseEntity.ok(TransactionMapper.toDTOs(transactions));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<TransactionResponseDTO> getTransactionById(
            @PathVariable @NotNull(message = "L'ID de la transaction ne peut pas être null") Long id) {
        Transaction transaction = transactionService.getTransactionById(id);
        return ResponseEntity.ok(TransactionMapper.toDTO(transaction));
    }

    @GetMapping("/reference/{reference}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<TransactionResponseDTO> getTransactionByReference(
            @PathVariable @NotNull(message = "La référence ne peut pas être null") String reference) {
        Transaction transaction = transactionService.getTransactionByReference(reference);
        return ResponseEntity.ok(TransactionMapper.toDTO(transaction));
    }

    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<TransactionResponseDTO> createTransaction(
            @Valid @RequestBody TransactionRequestDTO requestDTO) {
        Transaction createdTransaction = transactionService.createTransaction(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(TransactionMapper.toDTO(createdTransaction));
    }

    @GetMapping("/periode")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<TransactionResponseDTO>> getTransactionsByPeriod(
            @RequestParam @NotNull(message = "La date de début ne peut pas être null") 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime debut,
            @RequestParam @NotNull(message = "La date de fin ne peut pas être null") 
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin) {
        List<Transaction> transactions = transactionService.getTransactionsByPeriod(debut, fin);
        return ResponseEntity.ok(TransactionMapper.toDTOs(transactions));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body("Une erreur est survenue : " + e.getMessage());
    }
} 
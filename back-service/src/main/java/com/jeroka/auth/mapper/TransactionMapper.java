package com.jeroka.auth.mapper;

import com.jeroka.auth.dto.TransactionRequestDTO;
import com.jeroka.auth.dto.TransactionResponseDTO;
import com.jeroka.auth.model.Transaction;
import com.jeroka.auth.model.User;
import com.jeroka.auth.model.Abonnement;
import com.jeroka.auth.model.MethodePaiement;
import com.jeroka.auth.model.Facture;
import com.jeroka.auth.model.enums.StatutTransaction;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.List;
import java.util.Collections;
import java.util.stream.Collectors;

public class TransactionMapper {
    
    public static Transaction toEntity(TransactionRequestDTO dto, User user, 
                                     Abonnement abonnement, MethodePaiement methodePaiement) {
        Transaction transaction = new Transaction(
            user,
            dto.getType(),
            dto.getMontant(),
            generateReference(),
            dto.getPrestataire(),
            StatutTransaction.EN_ATTENTE
        );
        
        transaction.setAbonnement(abonnement);
        transaction.setMethodePaiement(methodePaiement);
        transaction.setDevise(dto.getDevise());
        transaction.setDescription(dto.getDescription());
        transaction.setMetadata(dto.getMetadata());
        
        return transaction;
    }
    
    public static TransactionResponseDTO toDTO(Transaction transaction) {
        if (transaction == null) {
            return null;
        }
        
        TransactionResponseDTO dto = new TransactionResponseDTO(
            transaction.getId(),
            transaction.getUser().getId(),
            transaction.getReference(),
            transaction.getType(),
            transaction.getMontant(),
            transaction.getDevise(),
            transaction.getStatut(),
            transaction.getPrestataire(),
            transaction.getDateTransaction()
        );
        
        if (transaction.getAbonnement() != null) {
            dto.setAbonnementId(transaction.getAbonnement().getId());
        }
        
        if (transaction.getMethodePaiement() != null) {
            dto.setMethodePaiementId(transaction.getMethodePaiement().getId());
        }
        
        if (transaction.getFacture() != null) {
            dto.setFactureId(transaction.getFacture().getId());
        }
        
        dto.setIdentifiantExterne(transaction.getIdentifiantExterne());
        dto.setDescription(transaction.getDescription());
        dto.setMetadata(transaction.getMetadata());
        dto.setCodeErreur(transaction.getCodeErreur());
        dto.setMessageErreur(transaction.getMessageErreur());
        
        return dto;
    }
    
    public static List<TransactionResponseDTO> toDTOs(List<Transaction> transactions) {
        if (transactions == null) {
            return Collections.emptyList();
        }
        return transactions.stream()
                .map(TransactionMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    private static String generateReference() {
        return "TRX-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
} 
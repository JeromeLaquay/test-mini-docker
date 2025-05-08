package com.jeroka.auth.service;

import com.jeroka.auth.dto.TransactionRequestDTO;
import com.jeroka.auth.mapper.TransactionMapper;
import com.jeroka.auth.model.Transaction;
import com.jeroka.auth.model.User;
import com.jeroka.auth.model.Abonnement;
import com.jeroka.auth.model.MethodePaiement;
import com.jeroka.auth.repository.TransactionRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@Validated
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private AbonnementService abonnementService;
    @Autowired
    private MethodePaiementService methodePaiementService;

    public List<Transaction> getTransactionsByUser(Long userId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            throw new EntityNotFoundException("Utilisateur non trouvé avec l'ID : " + userId);
        }
        return transactionRepository.findByUser(user);
    }

    public Transaction getTransactionById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("L'ID de la transaction ne peut pas être null");
        }
        return transactionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Transaction non trouvée avec l'ID : " + id));
    }

    public Transaction getTransactionByReference(String reference) {
        if (reference == null || reference.trim().isEmpty()) {
            throw new IllegalArgumentException("La référence ne peut pas être null ou vide");
        }
        return transactionRepository.findByReference(reference)
                .orElseThrow(() -> new EntityNotFoundException("Transaction non trouvée avec la référence : " + reference));
    }

    public Transaction createTransaction(@Valid TransactionRequestDTO requestDTO) {
        if (requestDTO == null) {
            throw new IllegalArgumentException("La requête de transaction ne peut pas être null");
        }

        User user = userService.getUserById(requestDTO.getUserId());
        if (user == null) {
            throw new EntityNotFoundException("Utilisateur non trouvé avec l'ID : " + requestDTO.getUserId());
        }

        Abonnement abonnement = null;
        if (requestDTO.getAbonnementId() != null) {
            abonnement = abonnementService.getAbonnementById(requestDTO.getAbonnementId());
            if (abonnement == null) {
                throw new EntityNotFoundException("Abonnement non trouvé avec l'ID : " + requestDTO.getAbonnementId());
            }
        }

        MethodePaiement methodePaiement = null;
        if (requestDTO.getMethodePaiementId() != null) {
            methodePaiement = methodePaiementService.getMethodePaiementById(requestDTO.getMethodePaiementId());
            if (methodePaiement == null) {
                throw new EntityNotFoundException("Méthode de paiement non trouvée avec l'ID : " + requestDTO.getMethodePaiementId());
            }
        }

        Transaction transaction = TransactionMapper.toEntity(requestDTO, user, abonnement, methodePaiement);
        return transactionRepository.save(transaction);
    }

    public List<Transaction> getTransactionsByPeriod(LocalDateTime debut, LocalDateTime fin) {
        if (debut == null || fin == null) {
            throw new IllegalArgumentException("Les dates de début et de fin ne peuvent pas être null");
        }
        if (debut.isAfter(fin)) {
            throw new IllegalArgumentException("La date de début doit être antérieure à la date de fin");
        }
        return transactionRepository.findByDateTransactionBetween(debut, fin);
    }
} 
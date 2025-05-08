package com.jeroka.auth.service;

import com.jeroka.auth.model.Facture;
import com.jeroka.auth.model.User;
import com.jeroka.auth.model.Transaction;
import com.jeroka.auth.repository.FactureRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class FactureService {
    @Autowired
    FactureRepository factureRepository;
    @Autowired
    UserService userService;
    @Autowired
    TransactionService transactionService;

    public List<Facture> getFacturesByUser(Long userId) {
        User user = userService.getUserById(userId);
        return factureRepository.findByUser(user);
    }

    public Facture getFactureById(Long id) {
        return factureRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Facture non trouvée avec l'ID : " + id));
    }

    public Facture getFactureByNumero(String numero) {
        return factureRepository.findByNumero(numero)
                .orElseThrow(() -> new EntityNotFoundException("Facture non trouvée avec le numéro : " + numero));
    }

    public Facture createFacture(Long userId, Long transactionId, Facture facture) {
        User user = userService.getUserById(userId);
        Transaction transaction = transactionService.getTransactionById(transactionId);

        facture.setUser(user);
        facture.setTransaction(transaction);
        facture.setDateCreation(LocalDateTime.now());
        
        if (facture.getDateEmission() == null) {
            facture.setDateEmission(LocalDate.now());
        }

        return factureRepository.save(facture);
    }

    public List<Facture> getFacturesByPeriode(LocalDate debut, LocalDate fin) {
        return factureRepository.findByDateEmissionBetween(debut, fin);
    }
} 
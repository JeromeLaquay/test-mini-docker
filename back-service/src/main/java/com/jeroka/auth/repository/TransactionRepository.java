package com.jeroka.auth.repository;

import com.jeroka.auth.model.Transaction;
import com.jeroka.auth.model.User;
import com.jeroka.auth.model.enums.StatutTransaction;
import com.jeroka.auth.model.enums.TypeTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUser(User user);
    List<Transaction> findByUserAndType(User user, TypeTransaction type);
    List<Transaction> findByUserAndStatut(User user, StatutTransaction statut);
    Optional<Transaction> findByReference(String reference);
    List<Transaction> findByDateTransactionBetween(LocalDateTime debut, LocalDateTime fin);
} 
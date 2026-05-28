package com.fintrack.fintrack_api.repository;

import com.fintrack.fintrack_api.model.Transaction;
import com.fintrack.fintrack_api.model.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByAccountId(Long accountId);
    List<Transaction> findByAccountIdAndType(Long accountId, TransactionType type);
    List<Transaction> findByAccountIdAndTransactionDateBetween(
            Long accountId,
            LocalDateTime start,
            LocalDateTime end
    );
}
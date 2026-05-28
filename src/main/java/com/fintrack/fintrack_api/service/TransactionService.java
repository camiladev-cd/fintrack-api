package com.fintrack.fintrack_api.service;

import com.fintrack.fintrack_api.dto.TransactionRequest;
import com.fintrack.fintrack_api.model.Account;
import com.fintrack.fintrack_api.model.Transaction;
import com.fintrack.fintrack_api.model.TransactionType;
import com.fintrack.fintrack_api.repository.AccountRepository;
import com.fintrack.fintrack_api.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    @Transactional
    public Transaction createTransaction(TransactionRequest request) {
        Account account = accountRepository.findById(request.getAccountId())
                .orElseThrow(() -> new RuntimeException("Account not found"));

        Transaction transaction = Transaction.builder()
                .amount(request.getAmount())
                .description(request.getDescription())
                .type(request.getType())
                .category(request.getCategory())
                .account(account)
                .build();

        if (request.getType() == TransactionType.INCOME) {
            account.setBalance(account.getBalance().add(request.getAmount()));
        } else if (request.getType() == TransactionType.EXPENSE) {
            account.setBalance(account.getBalance().subtract(request.getAmount()));
        }

        accountRepository.save(account);
        return transactionRepository.save(transaction);
    }

    public List<Transaction> getTransactionsByAccount(Long accountId) {
        return transactionRepository.findByAccountId(accountId);
    }

    public List<Transaction> getByAccountAndType(Long accountId, TransactionType type) {
        return transactionRepository.findByAccountIdAndType(accountId, type);
    }
}
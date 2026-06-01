package com.fintrack.fintrack_api.service;

import com.fintrack.fintrack_api.dto.AccountRequest;
import com.fintrack.fintrack_api.exception.ResourceNotFoundException;
import com.fintrack.fintrack_api.model.Account;
import com.fintrack.fintrack_api.model.User;
import com.fintrack.fintrack_api.repository.AccountRepository;
import com.fintrack.fintrack_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import com.fintrack.fintrack_api.model.Category;
import com.fintrack.fintrack_api.model.Transaction;
import com.fintrack.fintrack_api.model.TransactionType;
import com.fintrack.fintrack_api.repository.TransactionRepository;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    public Account createAccount(AccountRequest request) {
        User user = getCurrentUser();
        Account account = Account.builder()
                .name(request.getName())
                .type(request.getType())
                .balance(request.getInitialBalance() != null ? request.getInitialBalance() : BigDecimal.ZERO)
                .user(user)
                .build();
        Account saved = accountRepository.save(account);

        if (request.getInitialBalance() != null &&
                request.getInitialBalance().compareTo(BigDecimal.ZERO) > 0) {
            Transaction tx = Transaction.builder()
                    .amount(request.getInitialBalance())
                    .description("Saldo inicial")
                    .type(TransactionType.INCOME)
                    .category(Category.SAVINGS)
                    .account(saved)
                    .build();
            transactionRepository.save(tx);
        }

        return saved;
    }

    public List<Account> getMyAccounts() {
        User user = getCurrentUser();
        return accountRepository.findByUserId(user.getId());
    }

    public Account getAccountById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Account not found with id: " + id));
    }

    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext()
                .getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User not found"));
    }
}
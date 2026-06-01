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
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    public Account createAccount(AccountRequest request) {
        User user = getCurrentUser();
        Account account = Account.builder()
                .name(request.getName())
                .type(request.getType())
                .balance(request.getInitialBalance())
                .user(user)
                .build();
        return accountRepository.save(account);
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
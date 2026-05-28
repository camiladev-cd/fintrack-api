package com.fintrack.fintrack_api.controller;

import com.fintrack.fintrack_api.dto.AccountRequest;
import com.fintrack.fintrack_api.dto.AccountResponse;
import com.fintrack.fintrack_api.model.Account;
import com.fintrack.fintrack_api.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    private AccountResponse toResponse(Account a) {
        return new AccountResponse(
                a.getId(), a.getName(), a.getType(),
                a.getBalance(), a.getCreatedAt()
        );
    }

    @PostMapping
    public ResponseEntity<AccountResponse> create(
            @Valid @RequestBody AccountRequest request) {
        return ResponseEntity.ok(toResponse(
                accountService.createAccount(request)));
    }

    @GetMapping
    public ResponseEntity<List<AccountResponse>> getMyAccounts() {
        return ResponseEntity.ok(
                accountService.getMyAccounts()
                        .stream().map(this::toResponse).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(toResponse(
                accountService.getAccountById(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        accountService.deleteAccount(id);
        return ResponseEntity.noContent().build();
    }
}
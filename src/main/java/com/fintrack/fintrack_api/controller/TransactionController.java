package com.fintrack.fintrack_api.controller;

import com.fintrack.fintrack_api.dto.TransactionRequest;
import com.fintrack.fintrack_api.dto.TransactionResponse;
import com.fintrack.fintrack_api.model.Transaction;
import com.fintrack.fintrack_api.model.TransactionType;
import com.fintrack.fintrack_api.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    private TransactionResponse toResponse(Transaction t) {
        return new TransactionResponse(
                t.getId(), t.getAmount(), t.getDescription(),
                t.getType(), t.getCategory(),
                t.getTransactionDate(), t.getAccount().getId()
        );
    }

    @PostMapping
    public ResponseEntity<TransactionResponse> create(
            @Valid @RequestBody TransactionRequest request) {
        return ResponseEntity.ok(toResponse(
                transactionService.createTransaction(request)));
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<TransactionResponse>> getByAccount(
            @PathVariable Long accountId) {
        return ResponseEntity.ok(
                transactionService.getTransactionsByAccount(accountId)
                        .stream().map(this::toResponse).toList());
    }

    @GetMapping("/account/{accountId}/type/{type}")
    public ResponseEntity<List<TransactionResponse>> getByType(
            @PathVariable Long accountId,
            @PathVariable TransactionType type) {
        return ResponseEntity.ok(
                transactionService.getByAccountAndType(accountId, type)
                        .stream().map(this::toResponse).toList());
    }
}
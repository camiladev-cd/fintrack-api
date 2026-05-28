package com.fintrack.fintrack_api.dto;

import com.fintrack.fintrack_api.model.Category;
import com.fintrack.fintrack_api.model.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class TransactionResponse {
    private Long id;
    private BigDecimal amount;
    private String description;
    private TransactionType type;
    private Category category;
    private LocalDateTime transactionDate;
    private Long accountId;
}
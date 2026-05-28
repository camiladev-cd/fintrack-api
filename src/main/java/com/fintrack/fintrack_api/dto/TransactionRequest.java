package com.fintrack.fintrack_api.dto;

import com.fintrack.fintrack_api.model.Category;
import com.fintrack.fintrack_api.model.TransactionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class TransactionRequest {
    @NotNull
    private BigDecimal amount;
    @NotBlank
    private String description;
    private TransactionType type;
    private Category category;
    private Long accountId;
}
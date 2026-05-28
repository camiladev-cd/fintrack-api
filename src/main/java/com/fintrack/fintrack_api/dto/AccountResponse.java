package com.fintrack.fintrack_api.dto;

import com.fintrack.fintrack_api.model.AccountType;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AccountResponse {
    private Long id;
    private String name;
    private AccountType type;
    private BigDecimal balance;
    private LocalDateTime createdAt;
}
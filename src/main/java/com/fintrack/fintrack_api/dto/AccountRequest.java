package com.fintrack.fintrack_api.dto;

import com.fintrack.fintrack_api.model.AccountType;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class AccountRequest {
    @NotBlank
    private String name;
    private AccountType type;
    private BigDecimal initialBalance;
}
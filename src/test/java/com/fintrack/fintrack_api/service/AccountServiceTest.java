package com.fintrack.fintrack_api.service;

import com.fintrack.fintrack_api.dto.AccountRequest;
import com.fintrack.fintrack_api.model.Account;
import com.fintrack.fintrack_api.model.AccountType;
import com.fintrack.fintrack_api.model.User;
import com.fintrack.fintrack_api.repository.AccountRepository;
import com.fintrack.fintrack_api.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private SecurityContext securityContext;
    @Mock
    private Authentication authentication;

    @InjectMocks
    private AccountService accountService;

    private void mockSecurityContext(String email) {
        when(authentication.getName()).thenReturn(email);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void createAccount_shouldSaveAndReturnAccount() {
        mockSecurityContext("camila@test.com");

        User user = User.builder()
                .id(1L)
                .email("camila@test.com")
                .name("Camila")
                .password("encoded")
                .build();

        AccountRequest request = new AccountRequest();
        request.setName("Ahorros");
        request.setType(AccountType.SAVINGS);
        request.setInitialBalance(BigDecimal.valueOf(1000));

        Account savedAccount = Account.builder()
                .id(1L)
                .name("Ahorros")
                .type(AccountType.SAVINGS)
                .balance(BigDecimal.valueOf(1000))
                .user(user)
                .build();

        when(userRepository.findByEmail("camila@test.com")).thenReturn(Optional.of(user));
        when(accountRepository.save(any())).thenReturn(savedAccount);

        Account result = accountService.createAccount(request);

        assertNotNull(result);
        assertEquals("Ahorros", result.getName());
        assertEquals(BigDecimal.valueOf(1000), result.getBalance());
        verify(accountRepository, times(1)).save(any());
    }

    @Test
    void getMyAccounts_shouldReturnUserAccounts() {
        mockSecurityContext("camila@test.com");

        User user = User.builder().id(1L).email("camila@test.com").build();
        List<Account> accounts = List.of(
                Account.builder().id(1L).name("Ahorros").build(),
                Account.builder().id(2L).name("Corriente").build()
        );

        when(userRepository.findByEmail("camila@test.com")).thenReturn(Optional.of(user));
        when(accountRepository.findByUserId(1L)).thenReturn(accounts);

        List<Account> result = accountService.getMyAccounts();

        assertEquals(2, result.size());
        verify(accountRepository, times(1)).findByUserId(1L);
    }

    @Test
    void getAccountById_shouldThrowWhenNotFound() {
        when(accountRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () ->
                accountService.getAccountById(99L));
    }
}
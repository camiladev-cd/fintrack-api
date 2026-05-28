package com.fintrack.fintrack_api.service;

import com.fintrack.fintrack_api.dto.AuthResponse;
import com.fintrack.fintrack_api.dto.RegisterRequest;
import com.fintrack.fintrack_api.model.User;
import com.fintrack.fintrack_api.repository.UserRepository;
import com.fintrack.fintrack_api.security.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtService jwtService;
    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthService authService;

    @Test
    void register_shouldReturnTokenWhenSuccess() {
        RegisterRequest request = new RegisterRequest();
        request.setName("Camila");
        request.setEmail("camila@test.com");
        request.setPassword("123456");

        when(userRepository.existsByEmail(any())).thenReturn(false);
        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");
        when(userRepository.save(any())).thenReturn(new User());
        when(jwtService.generateToken(any())).thenReturn("fake-jwt-token");

        AuthResponse response = authService.register(request);

        assertNotNull(response);
        assertEquals("fake-jwt-token", response.getToken());
        verify(userRepository, times(1)).save(any());
    }

    @Test
    void register_shouldThrowWhenEmailAlreadyExists() {
        RegisterRequest request = new RegisterRequest();
        request.setEmail("camila@test.com");

        when(userRepository.existsByEmail(any())).thenReturn(true);

        assertThrows(RuntimeException.class, () -> authService.register(request));
        verify(userRepository, never()).save(any());
    }
}
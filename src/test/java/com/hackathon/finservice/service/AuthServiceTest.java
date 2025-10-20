package com.hackathon.finservice.service;

import com.hackathon.finservice.data.entity.Token;
import com.hackathon.finservice.data.entity.User;
import com.hackathon.finservice.data.repository.TokenRepository;
import com.hackathon.finservice.data.repository.UserRepository;
import com.hackathon.finservice.exception.InvalidTokenException;
import com.hackathon.finservice.security.CustomUserDetailsService;
import com.hackathon.finservice.security.JwtUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {
    @Mock private UserRepository userRepository;
    @Mock private TokenRepository tokenRepository;
    @Mock private PasswordEncoder passwordEncoder;
    @Mock private JwtUtil jwtUtil;
    @Mock private CustomUserDetailsService customUserDetailsService;

    @InjectMocks private AuthService authService;

    @Test
    void shouldAuthenticateUserAndGenerateJwt(){
        String identifier = "nuwe@nuwe.com";
        String password = "Nuwe123$";

        User user = new User();
        user.setEmail(identifier);
        user.setPassword("hashedPassword");

        when(userRepository.findByEmail(identifier)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(password, user.getPassword())).thenReturn(true);
        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(identifier).password("hashed").authorities("USER").build();
        when(customUserDetailsService.loadUserByUsername(identifier)).thenReturn(userDetails);
        when(jwtUtil.generateToken(userDetails)).thenReturn("jwtToken");

        String token = authService.authenticateUser(identifier, password);

        assertEquals("jwtToken", token);
        verify(tokenRepository).save(argThat(t -> t.getToken().equals("jwtToken")));
    }

    @Test
    void shouldThrowBadCredentials() {
        User user = new User();
        user.setEmail("nuwe@nuwe.com");
        user.setPassword("hashed");

        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        assertThrows(BadCredentialsException.class,
                () -> authService.authenticateUser(user.getEmail(), "wrongPassword"));
    }

    @Test
    void shouldRevokeTokenSuccessfully() {
        Token token = new Token();
        token.setToken("abc123");

        when(tokenRepository.findByToken("abc123")).thenReturn(Optional.of(token));
        authService.revokeToken("Bearer abc123");

        assertTrue(token.isRevoked());
        verify(tokenRepository).save(token);
    }

    @Test
    void shouldThrowWhenTokenNotFound() {
        when(tokenRepository.findByToken(anyString())).thenReturn(Optional.empty());

        assertThrows(InvalidTokenException.class, () -> authService.revokeToken("Invalid or expired token"));
    }
}

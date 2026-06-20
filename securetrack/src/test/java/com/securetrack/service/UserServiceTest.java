package com.securetrack.service;

import com.securetrack.dto.request.LoginRequest;
import com.securetrack.dto.request.RegisterRequest;
import com.securetrack.dto.response.JwtResponse;
import com.securetrack.dto.response.UserResponse;
import com.securetrack.entity.Role;
import com.securetrack.entity.User;
import com.securetrack.exception.DuplicateResourceException;
import com.securetrack.exception.UnauthorizedException;
import com.securetrack.repository.UserRepository;
import com.securetrack.security.JwtProvider;
import com.securetrack.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtProvider jwtProvider;

    @InjectMocks
    private UserServiceImpl userService;

    private RegisterRequest registerRequest;
    private LoginRequest loginRequest;
    private User user;

    @BeforeEach
    void setUp() {
        registerRequest = new RegisterRequest(
                "testuser", "test@email.com", "password123", "Test", "User"
        );

        loginRequest = new LoginRequest("testuser", "password123");

        user = new User(
                "testuser", "test@email.com", "encodedPassword", "Test", "User", Role.USER
        );
        user.setId(1L);
    }

    @Test
    void register_ShouldCreateUser_WhenValidRequest() {
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserResponse response = userService.register(registerRequest);

        assertNotNull(response);
        assertEquals("testuser", response.getUsername());
        assertEquals("test@email.com", response.getEmail());
        assertEquals(Role.USER, response.getRole());

        verify(userRepository).save(any(User.class));
    }

    @Test
    void register_ShouldThrowException_WhenUsernameExists() {
        when(userRepository.existsByUsername("testuser")).thenReturn(true);

        assertThrows(DuplicateResourceException.class, () -> {
            userService.register(registerRequest);
        });

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void register_ShouldThrowException_WhenEmailExists() {
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userRepository.existsByEmail("test@email.com")).thenReturn(true);

        assertThrows(DuplicateResourceException.class, () -> {
            userService.register(registerRequest);
        });

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void login_ShouldReturnToken_WhenValidCredentials() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("password123", "encodedPassword")).thenReturn(true);
        when(jwtProvider.generateToken(anyString(), anyString()))
                .thenReturn("valid-jwt-token");

        JwtResponse response = userService.login(loginRequest);

        assertNotNull(response);
        assertEquals("valid-jwt-token", response.getToken());
        assertEquals("testuser", response.getUsername());
    }

    @Test
    void login_ShouldThrowException_WhenInvalidPassword() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrongpassword", "encodedPassword")).thenReturn(false);

        assertThrows(UnauthorizedException.class, () -> {
            userService.login(new LoginRequest("testuser", "wrongpassword"));
        });
    }

    @Test
    void login_ShouldThrowException_WhenUserNotFound() {
        when(userRepository.findByUsername("nonexistent")).thenReturn(Optional.empty());

        assertThrows(UnauthorizedException.class, () -> {
            userService.login(new LoginRequest("nonexistent", "password"));
        });
    }
}
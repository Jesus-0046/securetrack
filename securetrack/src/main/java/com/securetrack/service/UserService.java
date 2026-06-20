package com.securetrack.service;

import com.securetrack.dto.request.LoginRequest;
import com.securetrack.dto.request.RegisterRequest;
import com.securetrack.dto.response.JwtResponse;
import com.securetrack.dto.response.UserResponse;

public interface UserService {

    UserResponse register(RegisterRequest request);

    JwtResponse login(LoginRequest request);

    UserResponse getUserById(Long id);

    UserResponse getUserByUsername(String username);
}
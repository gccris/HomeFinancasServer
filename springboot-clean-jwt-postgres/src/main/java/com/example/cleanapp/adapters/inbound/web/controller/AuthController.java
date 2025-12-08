package com.example.cleanapp.adapters.inbound.web.controller;

import com.example.cleanapp.application.dto.AuthRequest;
import com.example.cleanapp.application.dto.AuthResponse;
import com.example.cleanapp.application.port.in.UserUseCase;
import com.example.cleanapp.security.JwtTokenProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserUseCase userUseCase;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthController(UserUseCase userUseCase, JwtTokenProvider jwtTokenProvider) {
        this.userUseCase = userUseCase;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) {
        com.example.cleanapp.domain.model.User user = userUseCase.authenticate(authRequest);
        String token = jwtTokenProvider.generateToken(user.getUsername());
        return ResponseEntity.ok(new AuthResponse(token, user.getUsername(), user.getEmail()));
    }
}
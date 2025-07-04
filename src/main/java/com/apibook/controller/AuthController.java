package com.apibook.controller;

import com.apibook.dto.*;
import com.apibook.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest dto) {
        authService.register(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Usuario registrado");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest dto) {
        return ResponseEntity.ok(authService.login(dto));
    }

    @PostMapping("/register-admin")
    public ResponseEntity<String> regAdmin(@Valid @RequestBody RegisterRequest dto) {
        authService.registerAdmin(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("ADMIN registrado");
    }
}
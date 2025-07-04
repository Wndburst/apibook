package com.apibook.service;

import com.apibook.dto.*;
import com.apibook.entity.*;
import com.apibook.repository.*;
import com.apibook.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;

    public void register(RegisterRequest dto) {
        if (userRepo.existsByEmail(dto.email()))
            throw new RuntimeException("Email ya registrado");

        Role lectorRole = roleRepo.findByName(ERole.LECTOR)
                .orElseThrow(() -> new RuntimeException("Rol LECTOR no inicializado"));

        User user = new User();
        user.setEmail(dto.email());
        user.setName(dto.name());
        user.setLastName(dto.lastName());
        user.setPassword(encoder.encode(dto.password()));
        user.getRoles().add(lectorRole);
        userRepo.save(user);
    }

    public AuthResponse login(AuthRequest dto) {
        User user = userRepo.findByEmail(dto.email())
                .orElseThrow(() -> new RuntimeException("Credenciales inválidas"));

        if (!encoder.matches(dto.password(), user.getPassword()))
            throw new RuntimeException("Credenciales inválidas");

        String token = jwtUtils.generateToken(user.getEmail(), user.getRoles());
        long expiresAt = System.currentTimeMillis() + jwtUtils.getExpiration();

        return new AuthResponse(token, expiresAt);
    }
    public void registerAdmin(RegisterRequest dto) {
        if (userRepo.existsByEmail(dto.email()))
            throw new RuntimeException("Email ya registrado");

        Role adminRole = roleRepo.findByName(ERole.ADMIN)
                .orElseThrow(() -> new RuntimeException("Rol ADMIN no existe"));

        User user = new User();
        user.setEmail(dto.email());
        user.setName(dto.name());
        user.setLastName(dto.lastName());
        user.setPassword(encoder.encode(dto.password()));
        user.setState(true);
        user.getRoles().add(adminRole);

        userRepo.save(user);
    }
}
package com.apibook.dto;

public record AuthResponse(
        String token,
        long expiresAt
) {}
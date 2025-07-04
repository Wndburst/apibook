package com.apibook.dto;

import jakarta.validation.constraints.*;
public record AuthRequest(
        @Email String email,
        @NotBlank String password
) {}
package com.apibook.dto;

import jakarta.validation.constraints.*;

public record RegisterRequest(
        @Email String email,
        @NotBlank String name,
        @NotBlank String lastName,
        @Size(min = 6) String password
) {}

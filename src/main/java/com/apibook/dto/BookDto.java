package com.apibook.dto;

import jakarta.validation.constraints.NotBlank;

public record BookDto(
        @NotBlank String author,
        @NotBlank String title,
        @NotBlank String type,
        String image64          // opcional
) {}


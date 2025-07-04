package com.apibook.dto;

import jakarta.validation.constraints.NotNull;
public record BookingDto(
        @NotNull Integer copyBookId,
        @NotNull String  userEmail
) {}
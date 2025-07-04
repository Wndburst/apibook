package com.apibook.dto;

import jakarta.validation.constraints.NotNull;
public record ReturnDto(@NotNull Integer bookingId) {}
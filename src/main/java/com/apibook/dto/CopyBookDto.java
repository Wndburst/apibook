package com.apibook.dto;

import jakarta.validation.constraints.NotNull;

public record CopyBookDto(@NotNull Integer bookId) {}
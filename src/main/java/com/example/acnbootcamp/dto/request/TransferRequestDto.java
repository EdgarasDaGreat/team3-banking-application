package com.example.acnbootcamp.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.UUID;

public record TransferRequestDto(
        @NotNull
        UUID fromAccountId,

        @NotNull
        UUID toAccountId,

        @NotNull
        @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be greater than zero")
        BigDecimal amount,

        @Size(max = 255)
        String note
) {
}
package com.example.acnbootcamp.dto.response;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record TransferResponseDto(
        UUID fromAccountId,
        UUID toAccountId,
        BigDecimal amount,
        String note,
        Instant createdAt
) {
}
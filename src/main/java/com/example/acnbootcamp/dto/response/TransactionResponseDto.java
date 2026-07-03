package com.example.acnbootcamp.dto.response;

import com.example.acnbootcamp.domain.TransactionType;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record TransactionResponseDto(
        UUID transactionId,
        UUID accountId,
        TransactionType type,
        BigDecimal amount,
        Instant createdAt,
        String note
) {
}
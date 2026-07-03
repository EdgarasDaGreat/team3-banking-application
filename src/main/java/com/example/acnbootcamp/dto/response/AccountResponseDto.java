package com.example.acnbootcamp.dto.response;

import java.math.BigDecimal;
import java.util.UUID;

public record AccountResponseDto(
        UUID accountId,
        String iban,
        String ownerName,
        BigDecimal balance
) {
}
package com.example.acnbootcamp.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Data
@Builder
public class TransferResponse {
    private UUID fromAccountId;
    private UUID toAccountId;
    private BigDecimal amount;
    private String note;
    private Instant createdAt;
}
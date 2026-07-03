package com.example.acnbootcamp.domain;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Data
@Builder
public class Transaction {
    private UUID transactionId;
    private Account account;
    private TransactionType type;
    private BigDecimal amount;
    private Instant createdAt;
    private String note;
}

package com.example.acnbootcamp.domain;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class Account {
    private UUID accountId;
    private String iban;
    private String ownerName;
    private BigDecimal balance;
}

package com.example.acnbootcamp.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class AccountResponse {
    private UUID accountId;
    private String iban;
    private String ownerName;
    private BigDecimal balance;
}
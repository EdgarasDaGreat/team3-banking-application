package com.example.acnbootcamp.mapper;

import com.example.acnbootcamp.domain.Account;
import com.example.acnbootcamp.dto.request.CreateAccountRequest;
import com.example.acnbootcamp.dto.response.AccountResponse;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
public class AccountMapper {

    public Account toEntity(CreateAccountRequest request) {
        BigDecimal balance = request.getInitialBalance() != null
                ? request.getInitialBalance()
                : BigDecimal.ZERO;

        return Account.builder()
                .accountId(UUID.randomUUID())
                .iban(generateIban())
                .ownerName(request.getOwnerName())
                .balance(balance)
                .build();
    }

    public AccountResponse toResponse(Account account) {
        return AccountResponse.builder()
                .accountId(account.getAccountId())
                .iban(account.getIban())
                .ownerName(account.getOwnerName())
                .balance(account.getBalance())
                .build();
    }

    private String generateIban() {
        String digits = UUID.randomUUID().toString().replaceAll("[^0-9]", "");
        while (digits.length() < 16) {
            digits += UUID.randomUUID().toString().replaceAll("[^0-9]", "");
        }
        return "LT" + digits.substring(0, 18);
    }
}
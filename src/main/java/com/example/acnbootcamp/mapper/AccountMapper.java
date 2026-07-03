package com.example.acnbootcamp.mapper;

import com.example.acnbootcamp.domain.Account;
import com.example.acnbootcamp.dto.request.CreateAccountRequest;
import com.example.acnbootcamp.dto.response.AccountResponse;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
public class AccountMapper {

    public Account toEntity(UUID accountId, String iban, CreateAccountRequest request) {
        BigDecimal balance = request.getInitialBalance() != null
                ? request.getInitialBalance()
                : BigDecimal.ZERO;

        return Account.builder()
                .accountId(accountId)
                .iban(iban)
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
}
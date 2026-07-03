package com.example.acnbootcamp.mapper;

import com.example.acnbootcamp.domain.Account;
import com.example.acnbootcamp.dto.request.CreateAccountRequestDto;
import com.example.acnbootcamp.dto.response.AccountResponseDto;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
public class AccountMapper {

    public Account toEntity(UUID accountId, BigDecimal balance, String iban, CreateAccountRequestDto request) {
        return Account.builder()
                .accountId(accountId)
                .iban(iban)
                .ownerName(request.ownerName())
                .balance(balance)
                .build();
    }

    public AccountResponseDto toResponse(Account account) {
        return new AccountResponseDto(
                account.getAccountId(),
                account.getIban(),
                account.getOwnerName(),
                account.getBalance()
        );
    }
}
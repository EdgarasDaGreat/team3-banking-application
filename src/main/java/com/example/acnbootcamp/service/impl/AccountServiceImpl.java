package com.example.acnbootcamp.service.impl;

import com.example.acnbootcamp.domain.Account;
import com.example.acnbootcamp.dto.request.CreateAccountRequestDto;
import com.example.acnbootcamp.mapper.AccountMapper;
import com.example.acnbootcamp.repository.AccountRepository;
import com.example.acnbootcamp.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    @Override
    public Account createAccount(CreateAccountRequestDto request) {
        UUID newAccountId = UUID.randomUUID();
        String generatedIban = generateIban();

        Account account = accountMapper.toEntity(newAccountId, BigDecimal.ZERO, generatedIban, request);
        accountRepository.save(account);

        return account;
    }

    @Override
    public Account getAccountById(UUID accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account with ID " + accountId + " not found!"));
    }

    private String generateIban() {
        StringBuilder iban = new StringBuilder("LT");
        for (int i = 0; i < 18; i++) {
            iban.append(ThreadLocalRandom.current().nextInt(10));
        }
        return iban.toString();
    }
}
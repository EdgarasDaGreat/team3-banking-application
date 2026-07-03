package com.example.acnbootcamp.service;

import com.example.acnbootcamp.repository.AccountRepository;
import com.example.acnbootcamp.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountService
{
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public Account createAccount(CreateAccountRequest request)
    {
        UUID newAccountId = UUID.randomUUID();

        BigDecimal balance = request.getInitialBalance() != null ? request.getInitialBalance() : BigDecimal.ZERO;
        String generatedIban = generateMockIban();

        Account account = Account.builder()
                .accountId(newAccountId)
                .iban(generatedIban)
                .ownerName(request.getOwnerName())
                .balance(balance)
                .build();

       accountRepository.save(account);
       transactionRepository.initializedHistory(newAccountId);

        return account;
    }
    public Account getAccountById(UUID accountId)
    {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account with ID " + accountId + " not found!"));
    }

    private String generateMockIban() {
        return "LV99HABA" + UUID.randomUUID().toString().replaceAll("-", "").substring(0, 14).toUpperCase();
    }
}

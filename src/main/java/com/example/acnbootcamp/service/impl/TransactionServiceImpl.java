package com.example.acnbootcamp.service.impl;

import com.example.acnbootcamp.domain.Account;
import com.example.acnbootcamp.domain.Transaction;
import com.example.acnbootcamp.domain.TransactionType;
import com.example.acnbootcamp.dto.request.DepositRequestDto;
import com.example.acnbootcamp.dto.request.WithdrawalRequestDto;
import com.example.acnbootcamp.mapper.TransactionMapper;
import com.example.acnbootcamp.repository.AccountRepository;
import com.example.acnbootcamp.repository.TransactionRepository;
import com.example.acnbootcamp.service.AccountService;
import com.example.acnbootcamp.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final AccountService accountService;
    private final TransactionMapper transactionMapper;

    @Override
    public Transaction deposit(UUID accountId, DepositRequestDto request) {
        Account account = accountService.getAccountById(accountId);
        BigDecimal amount = request.amount();

        account.setBalance(account.getBalance().add(amount));
        accountRepository.save(account);

        Transaction transaction = transactionMapper.toEntity(
                UUID.randomUUID(), account, TransactionType.DEPOSIT, amount, Instant.now(), request.note());

        return transactionRepository.save(transaction);
    }

    @Override
    public Transaction withdraw(UUID accountId, WithdrawalRequestDto request) {
        Account account = accountService.getAccountById(accountId);
        BigDecimal amount = request.amount();

        if (account.getBalance().compareTo(amount) < 0) {
            throw new IllegalStateException("Insufficient funds for account ID: " + accountId);
        }

        account.setBalance(account.getBalance().subtract(amount));
        accountRepository.save(account);

        Transaction transaction = transactionMapper.toEntity(
                UUID.randomUUID(), account, TransactionType.WITHDRAWAL, amount.negate(), Instant.now(), request.note());

        return transactionRepository.save(transaction);
    }

    @Override
    public List<Transaction> getTransactionsForAccount(UUID accountId) {
        accountService.getAccountById(accountId);
        return transactionRepository.findByAccountId(accountId);
    }
}
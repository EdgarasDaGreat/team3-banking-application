package com.example.acnbootcamp.service;

import com.example.acnbootcamp.domain.Transaction;
import com.example.acnbootcamp.dto.request.DepositRequestDto;
import com.example.acnbootcamp.dto.request.WithdrawalRequestDto;

import java.util.List;
import java.util.UUID;

public interface TransactionService {

    Transaction deposit(UUID accountId, DepositRequestDto request);

    Transaction withdraw(UUID accountId, WithdrawalRequestDto request);

    List<Transaction> getTransactionsForAccount(UUID accountId);
}
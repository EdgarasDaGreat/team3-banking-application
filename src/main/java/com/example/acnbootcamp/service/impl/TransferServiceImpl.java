package com.example.acnbootcamp.service.impl;

import com.example.acnbootcamp.domain.Account;
import com.example.acnbootcamp.domain.Transaction;
import com.example.acnbootcamp.domain.TransactionType;
import com.example.acnbootcamp.dto.request.TransferRequestDto;
import com.example.acnbootcamp.dto.response.TransferResponseDto;
import com.example.acnbootcamp.exception.InsufficientFundsException;
import com.example.acnbootcamp.exception.InvalidTransferException;
import com.example.acnbootcamp.mapper.TransactionMapper;
import com.example.acnbootcamp.repository.AccountRepository;
import com.example.acnbootcamp.repository.TransactionRepository;
import com.example.acnbootcamp.service.AccountService;
import com.example.acnbootcamp.service.TransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final AccountService accountService;
    private final TransactionMapper transactionMapper;

    @Override
    public TransferResponseDto executeTransfer(TransferRequestDto request) {
        Account sourceAccount = accountService.getAccountById(request.fromAccountId());
        Account targetAccount = accountService.getAccountById(request.toAccountId());

        BigDecimal amount = request.amount();

        if (sourceAccount.getAccountId().equals(targetAccount.getAccountId())) {
            throw new InvalidTransferException("Cannot transfer money to the same account!");
        }
        synchronized (this) {
            if (sourceAccount.getBalance().compareTo(amount) < 0) {
                throw new InsufficientFundsException("Insufficient funds for account ID: " + sourceAccount.getAccountId());
            }
            sourceAccount.setBalance(sourceAccount.getBalance().subtract(amount));
            targetAccount.setBalance(targetAccount.getBalance().add(amount));

            accountRepository.save(sourceAccount);
            accountRepository.save(targetAccount);

            Instant now = Instant.now();

            Transaction debitTx = transactionMapper.toEntity(
                    UUID.randomUUID(), sourceAccount, TransactionType.TRANSFER, amount.negate(), now, request.note());
            Transaction creditTx = transactionMapper.toEntity(
                    UUID.randomUUID(), targetAccount, TransactionType.TRANSFER, amount, now, request.note());

            transactionRepository.save(debitTx);
            transactionRepository.save(creditTx);

            return transactionMapper.toTransferResponse(
                    sourceAccount.getAccountId(), targetAccount.getAccountId(), amount, request.note(), now);
        }
    }
}
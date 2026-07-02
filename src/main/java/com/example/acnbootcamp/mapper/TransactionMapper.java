package com.example.acnbootcamp.mapper;

import com.example.acnbootcamp.domain.Account;
import com.example.acnbootcamp.domain.Transaction;
import com.example.acnbootcamp.domain.TransactionType;
import com.example.acnbootcamp.dto.request.TransferRequest;
import com.example.acnbootcamp.dto.response.TransactionResponse;
import com.example.acnbootcamp.dto.response.TransferResponse;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class TransactionMapper {

    public TransactionResponse toResponse(Transaction transaction) {
        return TransactionResponse.builder()
                .transactionId(transaction.getTransactionId())
                .accountId(transaction.getAccount().getAccountId())
                .type(transaction.getType())
                .amount(transaction.getAmount())
                .createdAt(transaction.getCreatedAt())
                .note(transaction.getNote())
                .build();
    }

    public List<TransactionResponse> toResponseList(List<Transaction> transactions) {
        return transactions.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public Transaction toDebitTransaction(TransferRequest request, Account fromAccount) {
        return Transaction.builder()
                .transactionId(UUID.randomUUID())
                .account(fromAccount)
                .type(TransactionType.TRANSFER)
                .amount(request.getAmount().negate())
                .createdAt(Instant.now())
                .note(request.getNote())
                .build();
    }

    public Transaction toCreditTransaction(TransferRequest request, Account toAccount) {
        return Transaction.builder()
                .transactionId(UUID.randomUUID())
                .account(toAccount)
                .type(TransactionType.TRANSFER)
                .amount(request.getAmount())
                .createdAt(Instant.now())
                .note(request.getNote())
                .build();
    }

    public TransferResponse toTransferResponse(TransferRequest request, Transaction debit, Transaction credit) {
        return TransferResponse.builder()
                .fromAccountId(request.getFromAccountId())
                .toAccountId(request.getToAccountId())
                .amount(request.getAmount())
                .createdAt(credit.getCreatedAt())
                .note(request.getNote())
                .debitTransaction(toResponse(debit))
                .creditTransaction(toResponse(credit))
                .build();
    }
}
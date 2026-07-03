package com.example.acnbootcamp.mapper;

import com.example.acnbootcamp.domain.Account;
import com.example.acnbootcamp.domain.Transaction;
import com.example.acnbootcamp.domain.TransactionType;
import com.example.acnbootcamp.dto.response.TransactionResponse;
import com.example.acnbootcamp.dto.response.TransferResponse;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
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

    public Transaction toEntity(UUID id, Account account, TransactionType type,
                                BigDecimal amount, Instant createdAt, String note) {
        return Transaction.builder()
                .transactionId(id)
                .account(account)
                .type(type)
                .amount(amount)
                .createdAt(createdAt)
                .note(note)
                .build();
    }

    public TransferResponse toTransferResponse(UUID fromAccountId, UUID toAccountId,
                                               BigDecimal amount, String note, Instant createdAt) {
        return TransferResponse.builder()
                .fromAccountId(fromAccountId)
                .toAccountId(toAccountId)
                .amount(amount)
                .note(note)
                .createdAt(createdAt)
                .build();
    }
}
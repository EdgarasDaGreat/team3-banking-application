package com.example.acnbootcamp.mapper;

import com.example.acnbootcamp.domain.Account;
import com.example.acnbootcamp.domain.Transaction;
import com.example.acnbootcamp.domain.TransactionType;
import com.example.acnbootcamp.dto.response.TransactionResponseDto;
import com.example.acnbootcamp.dto.response.TransferResponseDto;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class TransactionMapper {

    public TransactionResponseDto toResponse(Transaction transaction) {
        return new TransactionResponseDto(
                transaction.getTransactionId(),
                transaction.getAccount().getAccountId(),
                transaction.getType(),
                transaction.getAmount(),
                transaction.getCreatedAt(),
                transaction.getNote()
        );
    }

    public List<TransactionResponseDto> toResponseList(List<Transaction> transactions) {
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

    public TransferResponseDto toTransferResponse(UUID fromAccountId, UUID toAccountId,
                                                  BigDecimal amount, String note, Instant createdAt) {
        return new TransferResponseDto(
                fromAccountId,
                toAccountId,
                amount,
                note,
                createdAt
        );
    }
}
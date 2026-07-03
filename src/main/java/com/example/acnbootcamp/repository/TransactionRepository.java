package com.example.acnbootcamp.repository;

import com.example.acnbootcamp.domain.Transaction;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class TransactionRepository {

    private final Map<UUID, Transaction> transactions = new HashMap<>();

    public Transaction save(Transaction transaction) {
        transactions.put(transaction.getTransactionId(), transaction);
        return transaction;
    }

    public Optional<Transaction> findById(UUID transactionId) {
        return Optional.ofNullable(transactions.get(transactionId));
    }

    public List<Transaction> findAll() {
        return new ArrayList<>(transactions.values());
    }

    public List<Transaction> findByAccountId(UUID accountId) {
        return transactions.values().stream()
                .filter(t -> t.getAccount().getAccountId().equals(accountId))
                .collect(Collectors.toList());
    }
}
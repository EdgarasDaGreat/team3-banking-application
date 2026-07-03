package com.example.acnbootcamp.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class TransactionRepository
{
    private final InMemoryStorage storage;

    public void initializedHistory(UUID accountId)
    {
        storage.getAccountTransactions().put(accountId, new ArrayList<Transaction>());
    }

    public void save(UUID accountId, Transaction transaction)
    {
        List<Transaction> transactions = storage.getAccountTransactions().get(accountId);

        if(transactions != null)
        {
            transactions.add(transaction);
        }
    }
    public List<Transaction> findByAccountId(UUID accountId)
    {
        return storage.getAccountTransactions().getOrDefault(accountId, List.<Transaction>of());
    }
}

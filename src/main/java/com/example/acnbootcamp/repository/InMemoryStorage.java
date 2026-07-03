package com.example.acnbootcamp.repository;

import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryStorage
{
    private final Map<UUID, Account> accounts = new ConcurrentHashMap<>();
    private final Map<UUID, List<Transaction>> accountTransactions = new ConcurrentHashMap<>();

    public Map<UUID, Account> getAccounts()
    {
        return accounts;
    }

    public Map<UUID, List<Transaction>> getAccountTransactions()
    {
        return accountTransactions;
    }
}

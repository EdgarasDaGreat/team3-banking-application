package com.example.acnbootcamp.repository;

import com.example.acnbootcamp.domain.Account;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class AccountRepository {

    private final Map<UUID, Account> accounts = new HashMap<>();

    public Account save(Account account) {
        accounts.put(account.getAccountId(), account);
        return account;
    }

    public Optional<Account> findById(UUID accountId) {
        return Optional.ofNullable(accounts.get(accountId));
    }

    public List<Account> findAll() {
        return new ArrayList<>(accounts.values());
    }

    public boolean existsById(UUID accountId) {
        return accounts.containsKey(accountId);
    }
}

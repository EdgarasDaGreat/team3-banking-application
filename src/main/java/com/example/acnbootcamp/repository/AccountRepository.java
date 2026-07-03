package com.example.acnbootcamp.repository;

import com.example.acnbootcamp.domain.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class AccountRepository
{

    private final InMemoryStorage storage;

    public Account save(Account account)
    {
        storage.getAccounts().put(account.getAccountId(), account);
        return account;
    }

    public Optional<Account> findById(UUID accountId)
    {
        return Optional.ofNullable(storage.getAccounts().get(accountId));
    }
}
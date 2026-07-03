package com.example.acnbootcamp.service;

import com.example.acnbootcamp.repository.AccountRepository;
import com.example.acnbootcamp.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransferService
{
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final AccountService accountService;

    public void executeTransfer(TransferRequest request)
    {
        Account sourceAccount =  accountService.getAccountById(request.getFromAccountId());
        Account targetAccount = accountService.getAccountById(request.getToAccountId());

        BigDecimal amount = request.getAmount();

        if(sourceAccount.getAccoutnId().equals(targetAccount.getAccountId()))
        {
            throw new IllegalArgumentException("Cannot transfer money to the same account!");
        }
        synchronized (this)
        {
            if(sourceAccount.getBalance().compareTo(amount) < 0)
            {
                throw new IllegalStateException("Insufficient funds for account ID: " + sourceAccount.getAccountId());
            }
            sourceAccount.setBalance(sourceAccount.getBalance().subtract(amount));
            targetAccount.setBalance(targetAccount.getBalance().add(amount));

            accountRepository.save(sourceAccount);
            accountRepository.save(targetAccount);

            Instant now = Instant.now();

            Transaction debitTx = Transaction.builder()
                    .transactionId(UUID.randomUUID())
                    .account(sourceAccount)
                    .type(TransactionType.DEBIT)
                    .amount(amount.negate())
                    .createdAt(now)
                    .note(request.getNote())
                    .build();
            Transaction creditTx = Transaction.builder()
                    .transactionId(UUID.randomUUID())
                    .account(targetAccount)
                    .type(TransactionType.CREDIT)
                    .amount(amount)
                    .createdAt(now)
                    .note(request.getNote())
                    .build();

            transactionRepository.save(sourceAccount.getAccountId(), debitTx);
            transactionRepository.save(targetAccount.getAccountId(), creditTx);

        }
    }
    public List<Transaction> getTransactionsForAccount(UUID accountId)
    {
        accountService.getAccountById(accountId);
        return transactionRepository.findByAccountId(accountId);
    }
}

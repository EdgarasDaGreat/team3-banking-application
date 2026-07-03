package com.example.acnbootcamp.controller;

import com.example.acnbootcamp.dto.request.CreateAccountRequestDto;
import com.example.acnbootcamp.dto.request.DepositRequestDto;
import com.example.acnbootcamp.dto.request.WithdrawalRequestDto;
import com.example.acnbootcamp.dto.response.AccountResponseDto;
import com.example.acnbootcamp.dto.response.TransactionResponseDto;
import com.example.acnbootcamp.mapper.AccountMapper;
import com.example.acnbootcamp.mapper.TransactionMapper;
import com.example.acnbootcamp.service.AccountService;
import com.example.acnbootcamp.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final TransactionService transactionService;
    private final AccountMapper accountMapper;
    private final TransactionMapper transactionMapper;

    @PostMapping
    public ResponseEntity<AccountResponseDto> createAccount(@Valid @RequestBody CreateAccountRequestDto request) {
        AccountResponseDto response = accountMapper.toResponse(accountService.createAccount(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponseDto> getAccount(@PathVariable UUID id) {
        return ResponseEntity.ok(accountMapper.toResponse(accountService.getAccountById(id)));
    }

    @GetMapping("/{id}/transactions")
    public ResponseEntity<List<TransactionResponseDto>> getAccountTransactions(@PathVariable UUID id) {
        return ResponseEntity.ok(transactionMapper.toResponseList(transactionService.getTransactionsForAccount(id)));
    }

    @PostMapping("/{id}/deposit")
    public ResponseEntity<TransactionResponseDto> deposit(@PathVariable UUID id, @Valid @RequestBody DepositRequestDto request) {
        TransactionResponseDto response = transactionMapper.toResponse(transactionService.deposit(id, request));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/{id}/withdraw")
    public ResponseEntity<TransactionResponseDto> withdraw(@PathVariable UUID id, @Valid @RequestBody WithdrawalRequestDto request) {
        TransactionResponseDto response = transactionMapper.toResponse(transactionService.withdraw(id, request));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
package com.example.acnbootcamp.service;

import com.example.acnbootcamp.domain.Account;
import com.example.acnbootcamp.dto.request.CreateAccountRequestDto;

import java.util.UUID;

public interface AccountService {

    Account createAccount(CreateAccountRequestDto request);

    Account getAccountById(UUID accountId);
}
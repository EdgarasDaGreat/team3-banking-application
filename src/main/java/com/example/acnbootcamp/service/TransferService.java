package com.example.acnbootcamp.service;

import com.example.acnbootcamp.dto.request.TransferRequestDto;

public interface TransferService {

    void executeTransfer(TransferRequestDto request);
}
package com.example.acnbootcamp.service;

import com.example.acnbootcamp.dto.request.TransferRequestDto;
import com.example.acnbootcamp.dto.response.TransferResponseDto;

public interface TransferService {

    TransferResponseDto executeTransfer(TransferRequestDto request);
}
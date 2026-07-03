package com.example.acnbootcamp.controller;

import com.example.acnbootcamp.dto.request.TransferRequestDto;
import com.example.acnbootcamp.dto.response.TransferResponseDto;
import com.example.acnbootcamp.service.TransferService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@RequestMapping("/transfer")
@RequiredArgsConstructor
public class TransferController {

    private final TransferService transferService;

    @PostMapping
    public ResponseEntity<TransferResponseDto> transfer(@Valid @RequestBody TransferRequestDto request) {
        transferService.executeTransfer(request);

        TransferResponseDto response = new TransferResponseDto(
                request.fromAccountId(), request.toAccountId(), request.amount(), request.note(), Instant.now());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
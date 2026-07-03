package com.example.acnbootcamp.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CreateAccountRequestDto(
        @NotBlank
        String ownerName
) {
}
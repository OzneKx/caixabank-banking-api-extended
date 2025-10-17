package com.hackathon.finservice.dto.transaction;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Cash transfer from the authenticated user's main account to another target account.")
public record TransferRequest(
        @Schema(description = "Amount of money to transfer.", example = "1500.00")
        BigDecimal amount,

        @Schema(description = "Account number of the recipient user.", example = "19b332")
        String targetAccountNumber
    ) {
}

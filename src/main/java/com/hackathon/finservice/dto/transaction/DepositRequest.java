package com.hackathon.finservice.dto.transaction;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Cash deposit into the authenticated user's main account.")
public record DepositRequest(
        @Schema(
            description = "Amount of money to deposit into the user's main account.",
            example = "2500.00"
        )
        BigDecimal amount
    ) {
}

package com.hackathon.finservice.dto.transaction;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Cash withdraw from the authenticated user's main account.")
public record WithdrawRequest(
        @Schema(
            description = "Amount of money to withdraw from the user's main account.",
            example = "500.00"
        )
        BigDecimal amount
    ) {
}

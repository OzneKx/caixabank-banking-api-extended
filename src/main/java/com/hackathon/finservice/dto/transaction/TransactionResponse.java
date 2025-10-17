package com.hackathon.finservice.dto.transaction;

import com.hackathon.finservice.data.entity.TransactionStatus;
import com.hackathon.finservice.data.entity.TransactionType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.Instant;

@Schema(description = "Transaction record associated with the authenticated user's account.")
public record TransactionResponse(
        @Schema(
            description = "Unique identifier for the transaction record.",
            example = "a93bd0f7-9d1b-4c3b-82b0-f13c9a8e3b5f"
        )
        Long id,


        @Schema(
            description = "Amount involved in the transaction.",
            example = "250.00"
        )
        BigDecimal amount,

        @Schema(
            description = "Type of transaction performed ('CASH_DEPOSIT', 'CASH_WITHDRAW', 'CASH_TRANSFER').",
            example = "CASH_DEPOSIT"
        )
        TransactionType transactionType,

        @Schema(
            description = "Status of transaction performed ('PENDING', 'APPROVED', 'FRAUD').",
            example = "PENDING"
        )
        TransactionStatus transactionStatus,

        @Schema(
            description = "Date and time when the transaction was processed.",
            example = "2025-10-16T14:25:43"
        )
        Instant transactionDate,

        @Schema(
            description = "Account number that originated the transaction.",
            example = "19b332"
        )
        String sourceAccountNumber,

        @Schema(
            description = "Account number of the destination account.",
            example = "e62fa2"
        )
        String targetAccountNumber
) {
}

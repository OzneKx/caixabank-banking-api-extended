package com.hackathon.finservice.dto.account;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Represents detailed information of a specific account, including its current balance and type.")
public record AccountDashboardResponse(
        @Schema(
            description = "Unique account identifier assigned automatically by the system.",
            example = "e62fa2"
        )
        String accountNumber,

        @Schema(
            description = "Current available balance of the account.",
            example = "35000.00"
        )
        BigDecimal balance,

        @Schema(
            description = "Type of the account. Possible values: 'Main' or 'Invest'.",
                example = "Main"
        )
        String accountType
    ) {
}

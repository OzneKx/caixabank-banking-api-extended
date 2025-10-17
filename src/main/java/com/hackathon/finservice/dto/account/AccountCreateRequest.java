package com.hackathon.finservice.dto.account;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Payload used to create a new bank account for the authenticated user.")
public record AccountCreateRequest(
        @NotBlank(message = "Main account number is required")
        @Schema(
            description = "Associates the account number to the new user automatically assigned by the system.",
            example = "e62fa2"
        )
        String accountNumber,

        @NotBlank(message = "Account type is required")
        @Schema(
            description = "Type of account to create. Accepts either 'Main' (automatically assigned) or 'Invest'.",
            example = "Main"
        )
        String accountType
    ) {
}

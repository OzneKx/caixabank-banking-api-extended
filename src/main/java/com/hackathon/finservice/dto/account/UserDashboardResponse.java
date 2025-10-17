package com.hackathon.finservice.dto.account;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Contains user profile information and associated account details.")
public record UserDashboardResponse(
        @Schema(description = "Name of the registered user.", example = "Nuwe Test")
        String name,

        @Schema(description = "Email address linked to the user account.", example = "nuwe@nuwe.com")
        String email,

        @Schema(description = "Main account number automatically assigned during registration.", example = "19b332")
        String accountNumber,

        @Schema(description = "Type of the main account (always 'Main' by default).", example = "Main")
        String accountType,

        @Schema(
            description = "Hashed version of the user's password for security purposes.",
            example = "$2a$10$vYWBxACqEIPeoT0O5b0faOHp4ITAHSBvoHDzBePW7tPqzpvqKLi6G"
        )
        String hashedPassword
    ) {
}

package com.hackathon.finservice.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Used for authenticating a user using email and password credentials.")
public record AuthRequest(
        @Schema(
                description = "Email address associated with the registered user account.",
                example = "nuwe@nuwe.com"
        )
        String identifier,

        @Schema(
            description = "User's password.",
            example = "Nuwe123$"
        )
        String password
    ) {
}

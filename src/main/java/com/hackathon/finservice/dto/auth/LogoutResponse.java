package com.hackathon.finservice.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Message confirming the user has been logged out.")
public record LogoutResponse(
        @Schema(
            description = "Logout confirmation message.",
            example = "User successfully logged out"
        )
        String message
    ) {
}

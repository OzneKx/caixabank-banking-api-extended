package com.hackathon.finservice.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Contains JWT token issued upon successful authentication.")
public record AuthResponse(
        @Schema(
            description = "Generated JWT token for the authenticated session.",
            example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJudXdlQG51d2UuY29tIiwiZXhwIjoxNjk1OTgxMDUxfQ.xxxxxxxxxxxxxx"
        )
        String token
    ) {
}

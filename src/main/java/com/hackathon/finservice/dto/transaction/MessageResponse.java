package com.hackathon.finservice.dto.transaction;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Generic message for simple operations results.")
public record MessageResponse(
        @Schema(
            description = "Message summarizing the outcome of the request.",
            example = "Cash deposited successfully"
        )
        String message
    ) {
}

package com.hackathon.finservice.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Used after successful user registration, including automatically created main account details.")
public class UserResponse {

    @Schema(
            description = "Name of registered user.",
            example = "Kenzo de Albuquerque"
    )
    String name;

    @Schema(
            description = "Email address used of authenticated user.",
            example = "kenzoalbuqk@gmail.com"
    )
    String email;

    @Schema(
        description = "Main account number automatically generated and assigned to the user.",
        example = "19b332"
    )
    String accountNumber;

    @Schema(
        description = "Type of account created for the user.",
        example = "Main"
    )
    String accountType;
}

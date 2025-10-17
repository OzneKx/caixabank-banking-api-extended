package com.hackathon.finservice.controller;

import com.hackathon.finservice.dto.auth.AuthRequest;
import com.hackathon.finservice.dto.auth.AuthResponse;
import com.hackathon.finservice.dto.auth.LogoutResponse;
import com.hackathon.finservice.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Authentication", description = "Endpoints for user login and logout using JWT token.")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(
        summary = "Authenticate user and obtain JWT token.",
        description = "Validates user credentials and returns a JWT token to be used for authorized API requests.",
        responses = {
            @ApiResponse(responseCode = "200", description = "User authenticated successfully"),
            @ApiResponse(responseCode = "401", description = "Bad credentials")
        }
    )
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) {
        String token = authService.authenticateUser(authRequest.identifier(), authRequest.password());
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @Operation(
        summary = "Logout user.",
        description = "Revokes the JWT token associated with the current session.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Logout successful"),
            @ApiResponse(responseCode = "401", description = "Invalid or expired token")
        }
    )
    @GetMapping("/logout")
    public ResponseEntity<LogoutResponse> logout(@RequestHeader("Authorization") String token) {
        authService.revokeToken(token);
        return ResponseEntity.ok(new LogoutResponse("Logout successful"));
    }
}

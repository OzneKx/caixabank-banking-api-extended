package com.hackathon.finservice.controller;

import com.hackathon.finservice.dto.account.AccountCreateRequest;
import com.hackathon.finservice.dto.account.AccountDashboardResponse;
import com.hackathon.finservice.dto.account.UserDashboardResponse;
import com.hackathon.finservice.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Accounts", description = "Endpoints related to bank account creation and management.")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @Operation(
        summary = "User dashboard data.",
        description = "Returns dashboard information for the authenticated user.",
        responses = {
            @ApiResponse(responseCode = "200", description = "User dashboard retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Access denied")
        }
    )
    @GetMapping("/dashboard/user")
    public ResponseEntity<UserDashboardResponse> getUserDashboardResponse() {
        UserDashboardResponse userDashboardResponse = accountService.getUserDashboardResponse();
        return ResponseEntity.ok(userDashboardResponse);
    }

    @Operation(
        summary = "Account dashboard data.",
        description = "Returns dashboard information for all accounts belonging to the authenticated user.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Account dashboard retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Access denied")
        }
    )
    @GetMapping("/dashboard/account")
    public ResponseEntity<AccountDashboardResponse> getAccountDashboardResponse() {
        AccountDashboardResponse accountDashboardResponse = accountService.getAccountDashboardResponse();
        return ResponseEntity.ok(accountDashboardResponse);
    }

    @Operation(
        summary = "Account dashboard data by index.",
        description = "Returns detailed information of a specific account based on its index.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Account retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Invalid index provided"),
            @ApiResponse(responseCode = "404", description = "Account not found")
        }
    )
    @GetMapping("/dashboard/account/{index}")
    public ResponseEntity<AccountDashboardResponse> getAccountByIndex(@PathVariable int index) {
        AccountDashboardResponse accountDashboardResponse = accountService.getAccountByIndex(index);
        return ResponseEntity.ok(accountDashboardResponse);
    }

    @Operation(
        summary = "Account creation.",
        description = "Allows an authenticated user to create an investment account to the default 'Main' account.",
        responses = {
            @ApiResponse(responseCode = "200", description = "New account added successfully for user"),
            @ApiResponse(responseCode = "401", description = "Access denied")
        }
    )
    @PostMapping("/account/create")
    public ResponseEntity<String> createNewAccount(@RequestBody AccountCreateRequest accountCreateRequest) {
        accountService.createNewAccount(accountCreateRequest);
        return ResponseEntity.ok("New account added successfully for user");
    }
}

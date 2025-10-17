package com.hackathon.finservice.controller;

import com.hackathon.finservice.dto.transaction.DepositRequest;
import com.hackathon.finservice.dto.transaction.MessageResponse;
import com.hackathon.finservice.dto.transaction.TransactionResponse;
import com.hackathon.finservice.dto.transaction.TransferRequest;
import com.hackathon.finservice.dto.transaction.WithdrawRequest;
import com.hackathon.finservice.service.MonitoringService;
import com.hackathon.finservice.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/account")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Transactions", description = "Endpoints for deposits, withdrawals, transfers, and transaction history.")
public class TransactionController {
    private final TransactionService transactionService;
    private final MonitoringService monitoringService;

    public TransactionController(TransactionService transactionService, MonitoringService monitoringService) {
        this.transactionService = transactionService;
        this.monitoringService = monitoringService;
    }

    @Operation(
        summary = "Cash deposit.",
        description = "Deposits the specified amount into the authenticated user's main account.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Cash deposited successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid amount")
        }
    )
    @PostMapping("/deposit")
    public ResponseEntity<MessageResponse> cashDepositTransaction(@RequestBody DepositRequest depositRequest) {
        MessageResponse messageResponse = transactionService.cashDepositTransaction(depositRequest);
        return ResponseEntity.ok(messageResponse);
    }

    @Operation(
        summary = "Cash withdraw.",
        description = "Withdraws the specified amount from the authenticated user's main account.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Cash withdrawn successfully"),
            @ApiResponse(responseCode = "400", description = "Insufficient balance")
        }
    )
    @PostMapping("/withdraw")
    public ResponseEntity<MessageResponse> cashWithdrawTransaction(@RequestBody WithdrawRequest withdrawRequest) {
        MessageResponse messageResponse = transactionService.cashWithdrawTransaction(withdrawRequest);
        return ResponseEntity.ok(messageResponse);
    }

    @Operation(
        summary = "Cash transfer.",
        description = "Transfers a specified amount from the user's main account to another account.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Fund transferred successfully"),
            @ApiResponse(responseCode = "400", description = "Insufficient balance")
        }
    )
    @PostMapping("/fund-transfer")
    public ResponseEntity<MessageResponse> cashTransferTransaction(@RequestBody TransferRequest transferRequest) {
        MessageResponse messageResponse = transactionService.cashTransferTransaction(transferRequest);
        return ResponseEntity.ok(messageResponse);
    }

    @Operation(
        summary = "Transaction history.",
        description = "Returns a list of all transactions associated with the authenticated user.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Transaction history retrieved successfully"),
        }
    )
    @GetMapping("/transactions")
    public ResponseEntity<List<TransactionResponse>> getUserTransactionHistory() {
        List<TransactionResponse> transactions = transactionService.getUserTransactionHistory();
        return ResponseEntity.ok(transactions);
    }
}

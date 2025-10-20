package com.hackathon.finservice.service;

import com.hackathon.finservice.data.entity.Account;
import com.hackathon.finservice.data.entity.Transaction;
import com.hackathon.finservice.data.entity.TransactionStatus;
import com.hackathon.finservice.data.entity.TransactionType;
import com.hackathon.finservice.data.entity.User;
import com.hackathon.finservice.data.mapper.TransactionMapper;
import com.hackathon.finservice.data.repository.AccountRepository;
import com.hackathon.finservice.data.repository.TransactionRepository;
import com.hackathon.finservice.data.repository.UserRepository;
import com.hackathon.finservice.dto.transaction.DepositRequest;
import com.hackathon.finservice.dto.transaction.MessageResponse;
import com.hackathon.finservice.dto.transaction.TransactionResponse;
import com.hackathon.finservice.dto.transaction.TransferRequest;
import com.hackathon.finservice.dto.transaction.WithdrawRequest;
import com.hackathon.finservice.exception.AccountNotFoundException;
import com.hackathon.finservice.exception.InsufficientBalanceException;
import com.hackathon.finservice.exception.InvalidAmountException;
import com.hackathon.finservice.exception.InvalidEmailException;
import com.hackathon.finservice.service.impl.TransactionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {
    @Mock private TransactionRepository transactionRepository;
    @Mock private AccountRepository accountRepository;
    @Mock private UserRepository userRepository;
    @Mock private TransactionMapper transactionMapper;

    @InjectMocks private TransactionServiceImpl transactionService;

    private User mockUser;
    private Account mainAccount;
    private Account targetAccount;

    @BeforeEach
    void setUp() {
        mockUser = new User();
        mockUser.setId(1L);
        mockUser.setEmail("kenzoalbuqk@gmail.com");

        mainAccount = new Account();
        mainAccount.setAccountType("Main");
        mainAccount.setAccountNumber("MAIN123");
        mainAccount.setBalance(BigDecimal.valueOf(500));
        mockUser.setAccounts(List.of(mainAccount));

        targetAccount = new Account();
        targetAccount.setAccountType("Main");
        targetAccount.setAccountNumber("TARGET123");

        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn(mockUser.getEmail());
        SecurityContext context = mock(SecurityContext.class);
        when(context.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(context);
    }

    @Test
    void shouldDepositSuccessfully() {
        DepositRequest depositRequest = new DepositRequest(BigDecimal.valueOf(100));

        when(userRepository.findByEmail(mockUser.getEmail())).thenReturn(Optional.of(mockUser));
        when(transactionMapper.toEntity(any(BigDecimal.class), eq(TransactionType.CASH_DEPOSIT)))
                .thenReturn(new Transaction());

        MessageResponse messageResponse = transactionService.cashDepositTransaction(depositRequest);

        assertEquals("Cash deposited successfully", messageResponse.message());
        verify(transactionRepository).save(any(Transaction.class));
    }

    @Test
    void shouldThrowInvalidAmountOnDeposit() {
        DepositRequest depositRequest = new DepositRequest(BigDecimal.ZERO);
        when(userRepository.findByEmail(mockUser.getEmail())).thenReturn(Optional.of(mockUser));

        assertThrows(InvalidAmountException.class,
                () -> transactionService.cashDepositTransaction(depositRequest));

        verify(transactionRepository, never()).save(any());
    }

    @Test
    void shouldWithdrawSuccessfully() {
        WithdrawRequest withdrawRequest = new WithdrawRequest(BigDecimal.valueOf(200));
        when(userRepository.findByEmail(mockUser.getEmail())).thenReturn(Optional.of(mockUser));
        when(transactionMapper.toEntity(any(BigDecimal.class), eq(TransactionType.CASH_WITHDRAW)))
                .thenReturn(new Transaction());

        MessageResponse response = transactionService.cashWithdrawTransaction(withdrawRequest);

        assertEquals("Cash withdrawn successfully", response.message());
        verify(transactionRepository).save(any(Transaction.class));
    }

    @Test
    void shouldThrowInsufficientBalanceOnWithdraw() {
        WithdrawRequest withdrawRequest = new WithdrawRequest(BigDecimal.valueOf(600));
        mainAccount.setBalance(BigDecimal.valueOf(100));
        when(userRepository.findByEmail(mockUser.getEmail())).thenReturn(Optional.of(mockUser));

        assertThrows(InsufficientBalanceException.class,
                () -> transactionService.cashWithdrawTransaction(withdrawRequest));
    }

    @Test
    void shouldThrowInvalidAmountOnWithdraw() {
        WithdrawRequest withdrawRequest = new WithdrawRequest(BigDecimal.ZERO);
        when(userRepository.findByEmail(mockUser.getEmail())).thenReturn(Optional.of(mockUser));

        assertThrows(InvalidAmountException.class,
                () -> transactionService.cashWithdrawTransaction(withdrawRequest));
    }

    @Test
    void shouldTransferFundsSuccessfully() {
        TransferRequest transferRequest = new TransferRequest(BigDecimal.valueOf(50), targetAccount.getAccountNumber());
        when(userRepository.findByEmail(mockUser.getEmail())).thenReturn(Optional.of(mockUser));
        when(accountRepository.findByAccountNumber(targetAccount.getAccountNumber())).thenReturn(Optional.of(targetAccount));
        when(transactionMapper.toEntity(any(BigDecimal.class), eq(TransactionType.CASH_TRANSFER)))
                .thenReturn(new Transaction());

        MessageResponse response = transactionService.cashTransferTransaction(transferRequest);

        assertEquals("Fund transferred successfully", response.message());
        verify(transactionRepository).save(any(Transaction.class));
    }

    @Test
    void shouldThrowAccountNotFoundOnTransfer() {
        TransferRequest transferRequest = new TransferRequest(BigDecimal.valueOf(100), "UNKNOWN");
        when(userRepository.findByEmail(mockUser.getEmail())).thenReturn(Optional.of(mockUser));
        when(accountRepository.findByAccountNumber("UNKNOWN")).thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class,
                () -> transactionService.cashTransferTransaction(transferRequest));
    }

    @Test
    void shouldThrowInsufficientBalanceOnTransfer() {
        TransferRequest transferRequest = new TransferRequest(BigDecimal.valueOf(100), targetAccount.getAccountNumber());
        mainAccount.setBalance(BigDecimal.valueOf(10));

        when(userRepository.findByEmail(mockUser.getEmail())).thenReturn(Optional.of(mockUser));
        when(accountRepository.findByAccountNumber(targetAccount.getAccountNumber())).thenReturn(Optional.of(targetAccount));

        assertThrows(InsufficientBalanceException.class,
                () -> transactionService.cashTransferTransaction(transferRequest));
    }

    @Test
    void shouldThrowInvalidAmountOnTransfer() {
        TransferRequest transferRequest = new TransferRequest(BigDecimal.ZERO, targetAccount.getAccountNumber());
        when(userRepository.findByEmail(mockUser.getEmail())).thenReturn(Optional.of(mockUser));

        assertThrows(InvalidAmountException.class,
                () -> transactionService.cashTransferTransaction(transferRequest));
    }

    @Test
    void shouldReturnTransactionHistorySuccessfully() {
        Transaction transaction = new Transaction();
        transaction.setId(1L);

        when(userRepository.findByEmail(mockUser.getEmail())).thenReturn(Optional.of(mockUser));
        when(transactionRepository.findAllByUserId(mockUser.getId())).thenReturn(List.of(transaction));
        when(transactionMapper.toResponse(any(Transaction.class)))
                .thenReturn(new TransactionResponse(
                        1L,
                        BigDecimal.TEN,
                        TransactionType.CASH_DEPOSIT,
                        TransactionStatus.PENDING,
                        Instant.now(),
                        "MAIN123",
                        "TARGET123"
                ));

        List<TransactionResponse> result = transactionService.getUserTransactionHistory();

        assertEquals(1, result.size());
        assertEquals(TransactionType.CASH_DEPOSIT, result.get(0).transactionType());
        verify(transactionRepository).findAllByUserId(mockUser.getId());
    }

    @Test
    void shouldThrowInvalidEmailWhenUserNotFound() {
        when(userRepository.findByEmail(mockUser.getEmail())).thenReturn(Optional.empty());

        assertThrows(InvalidEmailException.class,
                () -> transactionService.getUserTransactionHistory());
    }
}

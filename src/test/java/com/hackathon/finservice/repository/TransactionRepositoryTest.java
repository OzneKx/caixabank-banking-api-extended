package com.hackathon.finservice.repository;

import com.hackathon.finservice.data.entity.Account;
import com.hackathon.finservice.data.entity.Transaction;
import com.hackathon.finservice.data.entity.TransactionType;
import com.hackathon.finservice.data.entity.User;
import com.hackathon.finservice.data.repository.AccountRepository;
import com.hackathon.finservice.data.repository.TransactionRepository;
import com.hackathon.finservice.data.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ActiveProfiles("test")
class TransactionRepositoryTest {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    private User user;
    private Account account;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setName("Kenzo");
        user.setEmail("kenzo@bank.com");
        user.setPassword("123");
        userRepository.save(user);

        account = new Account();
        account.setAccountNumber("A001");
        account.setAccountType("Main");
        account.setBalance(BigDecimal.valueOf(500.0));
        account.setUser(user);
        accountRepository.save(account);
    }

    @Test
    void shouldSaveTransactionAndFindByUserId() {
        Transaction transaction = new Transaction();
        transaction.setAmount(BigDecimal.valueOf(100));
        transaction.setTransactionType(TransactionType.CASH_DEPOSIT);
        transaction.setSourceAccount(account);
        transaction.setTargetAccount(account);
        transactionRepository.save(transaction);

        List<Transaction> transactions = transactionRepository.findAllByUserId(user.getId());

        assertFalse(transactions.isEmpty(), "Transactions should not be empty");
        assertEquals(TransactionType.CASH_DEPOSIT, transactions.getFirst().getTransactionType());
        assertEquals(0, transactions.getFirst().getAmount().compareTo(BigDecimal.valueOf(100)));
    }

    @Test
    void shouldReturnEmptyListWhenUserHasNoTransactions() {
        User otherUser = new User();
        otherUser.setName("NoTx");
        otherUser.setEmail("no@tx.com");
        otherUser.setPassword("123");
        userRepository.save(otherUser);

        List<Transaction> transactions = transactionRepository.findAllByUserId(otherUser.getId());
        assertTrue(transactions.isEmpty());
    }
}

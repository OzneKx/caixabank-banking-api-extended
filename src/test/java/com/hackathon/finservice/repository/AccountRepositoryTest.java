package com.hackathon.finservice.repository;

import com.hackathon.finservice.data.entity.Account;
import com.hackathon.finservice.data.entity.User;
import com.hackathon.finservice.data.repository.AccountRepository;
import com.hackathon.finservice.data.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ActiveProfiles("test")
class AccountRepositoryTest {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp() {
        user = createUser();
    }

    @Test
    void shouldSaveAccountSuccessfully() {
        Account account = createAccount(user, "ACC123", 1000.0);
        Account saved = accountRepository.save(account);

        assertNotNull(saved.getId(), "Saved account should have an ID");
        assertEquals("ACC123", saved.getAccountNumber());
        assertEquals("Main", saved.getAccountType());
        assertEquals(BigDecimal.valueOf(1000.0), saved.getBalance());
    }

    @Test
    void shouldFindAccountByNumber() {
        createAccount(user, "ACC999", 800.0);
        Optional<Account> found = accountRepository.findByAccountNumber("ACC999");

        assertTrue(found.isPresent(), "Account should be found by account number");
        assertEquals("ACC999", found.get().getAccountNumber());
        assertEquals("Main", found.get().getAccountType());
        assertEquals(user.getId(), found.get().getUser().getId());
    }

    @Test
    void shouldReturnEmptyWhenAccountNumberDoesNotExist() {
        Optional<Account> result = accountRepository.findByAccountNumber("NOT_FOUND");

        assertTrue(result.isEmpty(), "Repository should return empty Optional when account not found");
    }

    private User createUser() {
        User user = new User();
        user.setName("Kenzo");
        user.setEmail("kenzo@bank.com");
        user.setPassword("123");
        return userRepository.save(user);
    }

    private Account createAccount(User accountOwner, String accountNumber, double accountBalance) {
        Account account = new Account();
        account.setUser(accountOwner);
        account.setAccountNumber(accountNumber);
        account.setBalance(BigDecimal.valueOf(accountBalance));
        account.setAccountType("Main");
        return accountRepository.save(account);
    }
}

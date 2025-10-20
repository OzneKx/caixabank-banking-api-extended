package com.hackathon.finservice.repository;

import com.hackathon.finservice.data.entity.User;
import com.hackathon.finservice.data.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        User user = new User();
        user.setName("Kenzo");
        user.setEmail("kenzo@test.com");
        user.setPassword("Strong1$");
        userRepository.save(user);
    }

    @Test
    void shouldFindUserByEmail() {
        Optional<User> found = userRepository.findByEmail("kenzo@test.com");
        assertTrue(found.isPresent());
        assertEquals("Kenzo", found.get().getName());
        assertEquals("kenzo@test.com", found.get().getEmail());
    }

    @Test
    void shouldReturnEmptyWhenEmailNotExists() {
        Optional<User> result = userRepository.findByEmail("notfound@test.com");
        assertTrue(result.isEmpty());
    }
}

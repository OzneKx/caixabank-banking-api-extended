package com.hackathon.finservice.service;

import com.hackathon.finservice.data.entity.Account;
import com.hackathon.finservice.data.entity.User;
import com.hackathon.finservice.data.mapper.UserMapper;
import com.hackathon.finservice.data.repository.UserRepository;
import com.hackathon.finservice.dto.user.UserRequest;
import com.hackathon.finservice.dto.user.UserResponse;
import com.hackathon.finservice.exception.EmailAlreadyExistsException;
import com.hackathon.finservice.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock private UserRepository userRepository;
    @Mock private UserMapper userMapper;
    @Mock private PasswordEncoder passwordEncoder;

    @InjectMocks private UserServiceImpl userService;

    @Test
    void shouldRegisterUserWithMainAccountSuccessfully() {
        UserRequest userRequest = new UserRequest("Kenzo", "kenzoalbuk@gmail.com", "Kenzo123$");

        User user = new User();
        user.setName(userRequest.name());
        user.setEmail(userRequest.email());
        user.setPassword(userRequest.password());

        when(userRepository.existsByEmail(userRequest.email())).thenReturn(false);
        when(passwordEncoder.encode(userRequest.password())).thenReturn("hashedPassword");
        when(userMapper.toEntity(userRequest)).thenReturn(user);
        when(userMapper.toResponse(any(User.class), any(Account.class))).thenReturn(new UserResponse(
                "Kenzo", "kenzoalbuk@gmail.com", "ACC123", "Main")
        );

        UserResponse userResponse = userService.registerUserWithMainAccount(userRequest);

        verify(userRepository, times(1)).save(any(User.class));
        assertEquals("Main", userResponse.accountType());
        assertEquals("Kenzo", userResponse.name());
        assertNotNull(userResponse.accountNumber());
    }

    @Test
    void shouldThrowWhenEmailAlreadyExists() {
        UserRequest userRequest = new UserRequest("Kenzo", "kenzoalbuk@gmail.com", "Kenzo123$");

        when(userRepository.existsByEmail(userRequest.email())).thenReturn(true);

        assertThrows(EmailAlreadyExistsException.class, () -> userService.registerUserWithMainAccount(userRequest));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void shouldEncodePasswordBeforeSaving() {
        UserRequest userRequest = new UserRequest("Kenzo", "kenzoalbuk@gmail.com", "Kenzo123$");

        when(userRepository.existsByEmail(userRequest.email())).thenReturn(false);
        when(passwordEncoder.encode(userRequest.password())).thenReturn("hashedPassword");
        when(userMapper.toEntity(userRequest)).thenReturn(new User());

        userService.registerUserWithMainAccount(userRequest);

        verify(passwordEncoder).encode(userRequest.password());
        verify(userRepository).save(argThat(u -> u.getPassword().equals("hashedPassword")));
    }
}

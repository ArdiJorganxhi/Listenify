package dev.ardijorganxhi.listenify.service;

import antlr.Token;
import dev.ardijorganxhi.listenify.entity.User;
import dev.ardijorganxhi.listenify.mapper.AuthMapper;
import dev.ardijorganxhi.listenify.model.request.LoginRequest;
import dev.ardijorganxhi.listenify.model.request.RegisterRequest;
import dev.ardijorganxhi.listenify.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private AuthMapper authMapper;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthService authService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private TokenService tokenService;

    @Test
    public void it_should_register() {
        RegisterRequest request = new RegisterRequest();
        request.setListenifyname("user1");
        request.setEmail("user1@gmail.com");
        request.setPassword("user123");

        User user = User.builder()
                .listenifyname(request.getListenifyname())
                .email(request.getEmail())
                .password(request.getPassword())
                .build();

        when(authMapper.register(request)).thenReturn(user);
        authService.register(request);
        verify(authMapper).register(request);
        verify(userRepository).save(user);

    }

    @Test
    public void it_should_login() throws Exception {
        LoginRequest request = new LoginRequest();
        request.setEmail("user1@gmail.com");
        request.setPassword("user123");

        User user = User.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .build();

        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(user));

        authService.login(request);

        verify(authenticationManager).authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        verify(userRepository).findByEmail(request.getEmail());
        verify(tokenService).generateToken(user);

    }




}

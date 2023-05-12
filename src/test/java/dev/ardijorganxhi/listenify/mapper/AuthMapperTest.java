package dev.ardijorganxhi.listenify.mapper;

import dev.ardijorganxhi.listenify.config.PasswordEncoder;
import dev.ardijorganxhi.listenify.entity.User;
import dev.ardijorganxhi.listenify.model.request.RegisterRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthMapperTest {

    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private AuthMapper authMapper;

    @Test
    public void it_should_register() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        when(passwordEncoder.bCryptPasswordEncoder()).thenReturn(bCryptPasswordEncoder);
        RegisterRequest request = RegisterRequest.builder()
                .listenifyname("user")
                .email("user@gmail.com")
                .password(bCryptPasswordEncoder.encode("user123"))
                .build();

        User user = User.builder()
                .listenifyname(request.getListenifyname())
                .email(request.getEmail())
                .password(bCryptPasswordEncoder.encode(request.getPassword()))
                .build();

        User result = authMapper.register(request);

        assertEquals(user.getListenifyname(), result.getListenifyname());
        assertEquals(user.getEmail(), result.getEmail());



    }
}

package dev.ardijorganxhi.listenify.mapper;

import dev.ardijorganxhi.listenify.config.PasswordEncoder;
import dev.ardijorganxhi.listenify.entity.User;
import dev.ardijorganxhi.listenify.model.request.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthMapper {
    private final PasswordEncoder passwordEncoder;
    public User register(RegisterRequest request) {
        return User.builder()
                .name(request.getName())
                .surname(request.getSurname())
                .email(request.getEmail())
                .password(passwordEncoder.bCryptPasswordEncoder().encode(request.getPassword()))
                .build();
    }
}

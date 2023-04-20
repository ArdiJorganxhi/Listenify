package dev.ardijorganxhi.listenify.mapper;

import dev.ardijorganxhi.listenify.entity.User;
import dev.ardijorganxhi.listenify.model.request.RegisterRequest;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User register(RegisterRequest request) {
        return User.builder()
                .name(request.getName())
                .surname(request.getSurname())
                .email(request.getEmail())
                .password(request.getPassword())
                .build();
    }
}

package dev.ardijorganxhi.listenify.service;

import dev.ardijorganxhi.listenify.entity.User;
import dev.ardijorganxhi.listenify.mapper.UserMapper;
import dev.ardijorganxhi.listenify.model.request.RegisterRequest;
import dev.ardijorganxhi.listenify.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public void register(RegisterRequest request) {
        User user = userMapper.register(request);
        userRepository.save(user);
    }
}

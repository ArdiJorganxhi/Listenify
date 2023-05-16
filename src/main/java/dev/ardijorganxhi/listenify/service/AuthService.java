package dev.ardijorganxhi.listenify.service;

import dev.ardijorganxhi.listenify.entity.User;
import dev.ardijorganxhi.listenify.mapper.AuthMapper;
import dev.ardijorganxhi.listenify.model.LoginResponse;
import dev.ardijorganxhi.listenify.model.request.LoginRequest;
import dev.ardijorganxhi.listenify.model.request.RegisterRequest;
import dev.ardijorganxhi.listenify.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final AuthMapper authMapper;

    public void register(RegisterRequest request) {
        User user = authMapper.register(request);
        userRepository.save(user);
    }

    public LoginResponse login(LoginRequest request) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        } catch (BadCredentialsException e) {
            throw new Exception("BadCredentials");
        }
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        return LoginResponse.builder()
                .message(tokenService.generateToken(user))
                .build();
    }
}

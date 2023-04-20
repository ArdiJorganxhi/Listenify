package dev.ardijorganxhi.listenify.service;

import dev.ardijorganxhi.listenify.entity.User;
import dev.ardijorganxhi.listenify.mapper.UserMapper;
import dev.ardijorganxhi.listenify.model.PagingResult;
import dev.ardijorganxhi.listenify.model.dto.UserDto;
import dev.ardijorganxhi.listenify.model.request.PaginationRequest;
import dev.ardijorganxhi.listenify.repository.UserRepository;
import dev.ardijorganxhi.listenify.repository.UserSpecification;
import dev.ardijorganxhi.listenify.utils.PaginationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public Optional<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Not found!"));
    }

    public PagingResult<UserDto> findAllUsers(PaginationRequest request) {
        final Pageable pageable = PaginationUtils.getPageable(request.getPage(), request.getSize(), request.getDirection(), request.getSortField());
        final Specification<User> specification = UserSpecification.getActiveUsers();
        final Page<User> userPage = userRepository.findAll(specification, pageable);
        final List<UserDto> users = userPage.stream().map(userMapper::toDto).toList();
        return new PagingResult<>(users,
                userPage.getTotalPages(),
                userPage.getTotalElements(),
                userPage.getSize(),
                userPage.getNumber(),
                userPage.isEmpty());

    }

    public UserDto findById(Long id) throws Exception {
        User user = userRepository.findById(id).orElseThrow();
        if(!user.isDeleted()) {
            return userMapper.toDto(user);
        } else {
            throw new Exception("User doesn't exist!");
        }

    }

    public void deleteUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow();
        user.setDeleted(true);
        userRepository.save(user);
    }
}

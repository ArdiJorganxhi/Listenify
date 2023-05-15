package dev.ardijorganxhi.listenify.repository;

import dev.ardijorganxhi.listenify.entity.User;
import dev.ardijorganxhi.listenify.model.request.PaginationRequest;
import dev.ardijorganxhi.listenify.service.UserService;
import dev.ardijorganxhi.listenify.utils.PaginationUtils;
import org.checkerframework.checker.nullness.Opt;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class UserRepositoryTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void it_should_find_by_email() {
        String email = "user@gmail.com";
        User user = User.builder()
                .email(email)
                .build();

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        Optional<User> result = userService.findByEmail(email);

        assertTrue(result.isPresent());
        assertEquals(user, result.get());
    }

    @Test
    public void it_should_find_all_with_specification_and_pagination() {
        User user1 = User.builder()
                .id(1L)
                .listenifyname("user1")
                .build();
        User user2 = User.builder()
                .id(2L)
                .listenifyname("user2")
                .build();

        List<User> users = List.of(user1, user2);

        PaginationRequest request = new PaginationRequest();

        Specification<User> specification = UserSpecification.getActiveUsers();
        Pageable pageable = PaginationUtils.getPageable(request.getPage(), request.getSize(), request.getDirection(), request.getSortField());
        Page<User> expectedPage = new PageImpl<>(users, pageable, users.size());

        when(userRepository.findAll(specification, pageable)).thenReturn(expectedPage);
    }


}

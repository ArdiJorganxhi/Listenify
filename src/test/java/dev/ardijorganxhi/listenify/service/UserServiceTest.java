package dev.ardijorganxhi.listenify.service;

import dev.ardijorganxhi.listenify.entity.User;
import dev.ardijorganxhi.listenify.mapper.UserMapper;
import dev.ardijorganxhi.listenify.model.PagingResult;
import dev.ardijorganxhi.listenify.model.dto.UserDto;
import dev.ardijorganxhi.listenify.model.request.PaginationRequest;
import dev.ardijorganxhi.listenify.repository.UserRepository;
import dev.ardijorganxhi.listenify.repository.UserSpecification;
import dev.ardijorganxhi.listenify.utils.PaginationUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;


    @Test
    public void it_should_find_all_users() {

        PaginationRequest paginationRequest = PaginationRequest.builder()
                .page(1)
                .size(10)
                .direction(Sort.Direction.ASC)
                .sortField("id")
                .build();

        User user1 = User.builder()
                .id(1L)
                .listenifyname("user1")
                .build();
        User user2 = User.builder()
                .id(2L)
                .listenifyname("user2")
                .build();

        List<User> users = List.of(user1, user2);

        UserDto userDto1 = UserDto.builder()
                .id(user1.getId())
                .listenifyname(user1.getListenifyname())
                .build();
        UserDto userDto2 = UserDto.builder()
                .id(user2.getId())
                .listenifyname(user2.getListenifyname())
                .build();

        List<UserDto> userDtos = List.of(userDto1, userDto2);

        Page<User> userPage = new PageImpl<>(users, PaginationUtils.getPageable(paginationRequest.getPage(), paginationRequest.getSize(), paginationRequest.getDirection(), paginationRequest.getSortField()), users.size());

        when(userRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(userPage);

        when(userMapper.toDto(user1)).thenReturn(userDto1);
        when(userMapper.toDto(user2)).thenReturn(userDto2);

        PagingResult<UserDto> result = userService.findAllUsers(paginationRequest);

        verify(userRepository).findAll(UserSpecification.getActiveUsers(), PaginationUtils.getPageable(paginationRequest.getPage(), paginationRequest.getSize(), paginationRequest.getDirection(), paginationRequest.getSortField()));

        verify(userMapper).toDto(user1);
        verify(userMapper).toDto(user2);

        assertEquals(userDtos, result.getContent());
        assertEquals(userPage.getTotalPages(), result.getTotalPages());
        assertEquals(userPage.getTotalElements(), result.getTotalElements());
        assertEquals(userPage.getSize(), result.getSize());
        assertEquals(userPage.isEmpty(), result.isEmpty());
    }

    @Test
    public void it_should_find_by_id() throws Exception {
        User user = User.builder()
                .id(1L)
                .listenifyname("user1")
                .email("user1@gmail.com")
                .deleted(false)
                .build();

        UserDto userDto = UserDto.builder()
                .id(user.getId())
                .listenifyname(user.getListenifyname())
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.toDto(user)).thenReturn(userDto);

        UserDto result = userService.findById(1L);

        assertEquals(userDto.getId(), result.getId());
        assertEquals(userDto.getListenifyname(), result.getListenifyname());

        verify(userRepository).findById(1L);
        verify(userMapper).toDto(user);
    }

    @Test
    public void it_should_delete_by_id() {
        User user = User.builder()
                .id(1L)
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userService.deleteUserById(1L);

        assertTrue(user.isDeleted());
        verify(userRepository).save(user);
    }


}

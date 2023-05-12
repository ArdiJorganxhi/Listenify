package dev.ardijorganxhi.listenify.mapper;

import dev.ardijorganxhi.listenify.config.PasswordEncoder;
import dev.ardijorganxhi.listenify.entity.User;
import dev.ardijorganxhi.listenify.model.dto.UserDto;
import dev.ardijorganxhi.listenify.model.request.RegisterRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserMapperTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserMapper userMapper;

    @Test
    public void it_should_convert_to_dto() {
        User user = User.builder()
                .id(1L)
                .listenifyname("user")
                .email("user@gmail.com")
                .build();

        UserDto userDto = UserDto.builder()
                .id(1L)
                .listenifyname("user")
                .build();

        UserDto result = userMapper.toDto(user);

        assertEquals(userDto.getId(), result.getId());
        assertEquals(userDto.getListenifyname(), result.getListenifyname());

    }

    @Test
    public void it_should_convert_list_to_dto() {
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
                .id(1L)
                .listenifyname("user1")
                .build();
        UserDto userDto2 = UserDto.builder()
                .id(2L)
                .listenifyname("user2")
                .build();

        List<UserDto> userDtos = List.of(userDto1, userDto2);

        List<UserDto> result = userMapper.listToDto(users);

        assertEquals(userDtos.get(0).getId(), result.get(0).getId());
        assertEquals(userDtos.get(1).getId(), result.get(1).getId());
        assertEquals(userDtos.get(0).getListenifyname(), result.get(0).getListenifyname());
        assertEquals(userDtos.get(1).getListenifyname(), result.get(1).getListenifyname());
    }
}

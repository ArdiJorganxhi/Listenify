package dev.ardijorganxhi.listenify.mapper;

import dev.ardijorganxhi.listenify.entity.User;
import dev.ardijorganxhi.listenify.model.dto.UserDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public UserDto toDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .listenifyname(user.getListenifyname())
                .build();
    }

    public List<UserDto> listToDto(List<User> users) {
        return users.stream().map(this::toDto).collect(Collectors.toList());
    }
}

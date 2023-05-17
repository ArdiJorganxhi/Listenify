package dev.ardijorganxhi.listenify.controller;

import dev.ardijorganxhi.listenify.entity.User;
import dev.ardijorganxhi.listenify.model.PagingResult;
import dev.ardijorganxhi.listenify.model.dto.PlaylistDto;
import dev.ardijorganxhi.listenify.model.dto.UserDto;
import dev.ardijorganxhi.listenify.model.request.PaginationRequest;
import dev.ardijorganxhi.listenify.service.PlaylistService;
import dev.ardijorganxhi.listenify.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.MDC;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static dev.ardijorganxhi.listenify.utils.MdcConstant.X_USER_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private PlaylistService playlistService;

    @InjectMocks
    private UserController userController;

    @Test
    public void it_should_get_all_users() {
        PaginationRequest request = new PaginationRequest();

        UserDto userDto = UserDto.builder()
                .id(1L)
                .listenifyname("user1")
                .build();
        UserDto userDto2 = UserDto.builder()
                .id(2L)
                .listenifyname("user2")
                .build();

        List<UserDto> userDtos = List.of(userDto, userDto2);

        PagingResult<UserDto> pagingResult = new PagingResult<>();
        pagingResult.setContent(userDtos);
        pagingResult.setTotalPages(1);

        when(userService.findAllUsers(request)).thenReturn(pagingResult);

        ResponseEntity<PagingResult<UserDto>> response = userController.findAllUsers(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pagingResult, response.getBody());

    }

    @Test
    public void it_should_get_profile() throws Exception {
        Long userId = 1L;

        MDC.put(X_USER_ID, String.valueOf(userId));

        UserDto userDto = UserDto.builder()
                .id(userId)
                .listenifyname("user")
                .build();

        when(userService.findById(userId)).thenReturn(userDto);

        ResponseEntity<UserDto> response = userController.getProfile();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userDto, response.getBody());
    }

    @Test
    public void it_should_find_by_id() throws Exception {
        Long userId = 1L;

        UserDto userDto = UserDto.builder()
                .id(userId)
                .listenifyname("user")
                .build();

        when(userService.findById(userId)).thenReturn(userDto);

        ResponseEntity<UserDto> response = userController.getUserById(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userDto, response.getBody());
    }

    @Test
    public void it_should_delete_by_id() {
        Long userId = 1L;

        userController.deleteUserById(userId);

        verify(userService).deleteUserById(userId);
    }

    @Test
    public void it_should_get_user_playlists() {
        Long userId = 1L;

        PaginationRequest request = new PaginationRequest();

        PlaylistDto playlistDto = PlaylistDto.builder()
                .id(1L)
                .name("playlist")
                .build();

        PagingResult<PlaylistDto> pagingResult = new PagingResult<>();
        pagingResult.setContent(Collections.singletonList(playlistDto));
        pagingResult.setTotalPages(1);

        when(playlistService.getPlaylists(userId, request)).thenReturn(pagingResult);

        ResponseEntity<PagingResult<PlaylistDto>> response = userController.getPlaylists(userId, request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pagingResult, response.getBody());





    }


}

package dev.ardijorganxhi.listenify.controller;

import dev.ardijorganxhi.listenify.model.PagingResult;
import dev.ardijorganxhi.listenify.model.dto.UserDto;
import dev.ardijorganxhi.listenify.model.request.PaginationRequest;
import dev.ardijorganxhi.listenify.service.UserService;
import dev.ardijorganxhi.listenify.utils.MdcConstant;
import lombok.RequiredArgsConstructor;
import org.slf4j.MDC;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/list")
    private ResponseEntity<PagingResult<UserDto>> findAllUsers(PaginationRequest request) {
        return ResponseEntity.ok(userService.findAllUsers(request));
    }

    @GetMapping
    private ResponseEntity<UserDto> getProfile() throws Exception {
        return ResponseEntity.ok(userService.findById(Long.valueOf(MDC.get(MdcConstant.X_USER_ID))));
    }

    @GetMapping("/{id}")
    private ResponseEntity<UserDto> getUserById(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(userService.findById(id));
    }

    @DeleteMapping("/{id}")
    private void deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);
    }
}

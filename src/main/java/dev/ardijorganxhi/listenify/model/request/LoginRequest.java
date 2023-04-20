package dev.ardijorganxhi.listenify.model.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    private String email;
    private String password;
}

package dev.ardijorganxhi.listenify.model.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    private String listenifyname;
    private String email;
    private String password;
}

package dev.ardijorganxhi.listenify.model.request;


import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    @NotBlank(message = "Email cannot be null.")
    private String email;

    @NotBlank(message = "Password cannot be null.")
    private String password;
}

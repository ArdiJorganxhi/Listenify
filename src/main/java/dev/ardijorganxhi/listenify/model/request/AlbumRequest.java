package dev.ardijorganxhi.listenify.model.request;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlbumRequest {

    @NotBlank(message = "Album name cannot be null.")
    private String name;
}

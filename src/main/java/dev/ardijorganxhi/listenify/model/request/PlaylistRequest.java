package dev.ardijorganxhi.listenify.model.request;

import lombok.*;

import javax.persistence.GeneratedValue;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class PlaylistRequest {

    @NotBlank(message = "Playlist name cannot be null.")
    private String name;
}

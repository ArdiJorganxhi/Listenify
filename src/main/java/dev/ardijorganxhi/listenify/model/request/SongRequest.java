package dev.ardijorganxhi.listenify.model.request;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class SongRequest {

    @NotBlank(message = "Song name cannot be null.")
    private String name;

    @NotBlank(message = "Genre of song cannot be null.")
    private String genre;
}

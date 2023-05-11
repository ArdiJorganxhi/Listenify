package dev.ardijorganxhi.listenify.model.request;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class ArtistRequest {

    @NotBlank(message = "Artist name cannot be null.")
    private String listenifyname;
}

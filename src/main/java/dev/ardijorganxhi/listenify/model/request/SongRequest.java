package dev.ardijorganxhi.listenify.model.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class SongRequest {

    private String name;
    private String genre;
}

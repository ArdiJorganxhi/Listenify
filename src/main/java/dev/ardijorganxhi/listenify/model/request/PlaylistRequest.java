package dev.ardijorganxhi.listenify.model.request;

import lombok.*;

import javax.persistence.GeneratedValue;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class PlaylistRequest {

    private String name;
}

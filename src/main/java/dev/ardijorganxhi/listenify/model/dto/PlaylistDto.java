package dev.ardijorganxhi.listenify.model.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class PlaylistDto {

    private Long id;
    private String name;
}

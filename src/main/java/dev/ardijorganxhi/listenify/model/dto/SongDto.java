package dev.ardijorganxhi.listenify.model.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class SongDto {

    private Long id;
    private String name;
}

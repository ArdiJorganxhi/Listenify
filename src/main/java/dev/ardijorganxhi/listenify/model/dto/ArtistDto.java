package dev.ardijorganxhi.listenify.model.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class ArtistDto {

    private Long id;
    private String listenifyname;

}

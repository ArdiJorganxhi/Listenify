package dev.ardijorganxhi.listenify.mapper;

import dev.ardijorganxhi.listenify.entity.Artist;
import dev.ardijorganxhi.listenify.model.dto.ArtistDto;
import dev.ardijorganxhi.listenify.model.request.ArtistRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ArtistMapper {

    public Artist registerArtist(ArtistRequest request) {
        return Artist.builder()
                .listenifyname(request.getListenifyname())
                .build();
    }

    public ArtistDto toDto(Artist artist) {
        return ArtistDto.builder()
                .id(artist.getId())
                .listenifyname(artist.getListenifyname())
                .build();
    }

    public List<ArtistDto> listToDto(List<Artist> artists) {
        return artists.stream().map(this::toDto).collect(Collectors.toList());
    }
}

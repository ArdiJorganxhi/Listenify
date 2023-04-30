package dev.ardijorganxhi.listenify.mapper;

import dev.ardijorganxhi.listenify.entity.Album;
import dev.ardijorganxhi.listenify.entity.Artist;
import dev.ardijorganxhi.listenify.model.dto.AlbumDto;
import dev.ardijorganxhi.listenify.model.request.AlbumRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AlbumMapper {

    private final ArtistMapper artistMapper;
    private final SongMapper songMapper;

    public Album createAlbum(AlbumRequest request, Artist artist) {
        return Album.builder()
                .name(request.getName())
                .artist(artist)
                .build();
    }

    public AlbumDto toDto(Album album) {
        return AlbumDto.builder()
                .id(album.getId())
                .name(album.getName())
                .build();
    }

    public List<AlbumDto> listToDto(List<Album> albums) {
        return albums.stream().map(this::toDto).collect(Collectors.toList());
    }
}

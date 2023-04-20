package dev.ardijorganxhi.listenify.mapper;

import dev.ardijorganxhi.listenify.entity.Artist;
import dev.ardijorganxhi.listenify.entity.Song;
import dev.ardijorganxhi.listenify.model.dto.SongDto;
import dev.ardijorganxhi.listenify.model.request.SongRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SongMapper {

    private final ArtistMapper artistMapper;
    public Song registerSong(SongRequest request, Artist artist) {
        return Song.builder()
                .name(request.getName())
                .genre(request.getGenre())
                .artist(artist)
                .build();
    }

    public SongDto toDto(Song song) {
        return SongDto.builder()
                .id(song.getId())
                .name(song.getName())
                .artist(artistMapper.toDto(song.getArtist()))
                .build();
    }

    public List<SongDto> listToDto(List<Song> songs) {
        return songs.stream().map(this::toDto).collect(Collectors.toList());
    }
}

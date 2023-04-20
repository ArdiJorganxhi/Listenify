package dev.ardijorganxhi.listenify.mapper;

import dev.ardijorganxhi.listenify.entity.Artist;
import dev.ardijorganxhi.listenify.entity.Song;
import dev.ardijorganxhi.listenify.model.request.SongRequest;
import org.springframework.stereotype.Component;

@Component
public class SongMapper {

    public Song registerSong(SongRequest request, Artist artist) {
        return Song.builder()
                .name(request.getName())
                .genre(request.getGenre())
                .artist(artist)
                .build();
    }
}

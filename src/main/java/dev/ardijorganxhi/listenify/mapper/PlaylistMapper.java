package dev.ardijorganxhi.listenify.mapper;

import dev.ardijorganxhi.listenify.entity.Playlist;
import dev.ardijorganxhi.listenify.entity.SongPlaylist;
import dev.ardijorganxhi.listenify.entity.Song;
import dev.ardijorganxhi.listenify.entity.User;
import dev.ardijorganxhi.listenify.model.dto.PlaylistDto;
import dev.ardijorganxhi.listenify.model.request.PlaylistRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PlaylistMapper {

    public Playlist createPlaylist(User user, PlaylistRequest request) {
        return Playlist.builder()
                .name(request.getName())
                .user(user)
                .build();
    }

    public SongPlaylist addSongToPlaylist(Playlist playlist, Song song) {
        SongPlaylist songPlaylist = new SongPlaylist();
        songPlaylist.setPlaylist(playlist);
        songPlaylist.setSong(song);
        return songPlaylist;
    }

    public PlaylistDto toDto(Playlist playlist) {
        return PlaylistDto.builder()
                .id(playlist.getId())
                .name(playlist.getName())
                .build();
    }

    public List<PlaylistDto> listToDto(List<Playlist> playlists) {
        return playlists.stream().map(this::toDto).collect(Collectors.toList());
    }
}

package dev.ardijorganxhi.listenify.mapper;

import dev.ardijorganxhi.listenify.entity.Playlist;
import dev.ardijorganxhi.listenify.entity.Song;
import dev.ardijorganxhi.listenify.entity.SongPlaylist;
import dev.ardijorganxhi.listenify.entity.User;
import dev.ardijorganxhi.listenify.model.dto.PlaylistDto;
import dev.ardijorganxhi.listenify.model.request.PlaylistRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class PlaylistMapperTest {

    @InjectMocks
    private PlaylistMapper playlistMapper;

    @Test
    public void it_should_register() {
        User user = User.builder()
                .id(1L)
                .listenifyname("user")
                .build();

        PlaylistRequest request = PlaylistRequest.builder()
                .name("playlist")
                .build();

        Playlist playlist = Playlist.builder()
                .name(request.getName())
                .user(user)
                .build();

        Playlist result = playlistMapper.createPlaylist(user, request);

        assertEquals(playlist.getName(), result.getName());
        assertEquals(playlist.getUser(), result.getUser());
    }

    @Test
    public void it_should_add_song_to_playlist() {
        Playlist playlist = Playlist.builder()
                .id(1L)
                .name("playlist")
                .build();

        Song song = Song.builder()
                .id(1L)
                .name("song")
                .build();

        SongPlaylist songPlaylist = SongPlaylist.builder()
                .song(song)
                .playlist(playlist)
                .build();

        SongPlaylist result = playlistMapper.addSongToPlaylist(playlist, song);

        assertEquals(songPlaylist.getPlaylist(), result.getPlaylist());
        assertEquals(songPlaylist.getSong(), result.getSong());
    }

    @Test
    public void it_should_convert_to_dto() {
        Playlist playlist = Playlist.builder()
                .id(1L)
                .name("playlist")
                .build();

        PlaylistDto playlistDto = PlaylistDto.builder()
                .id(playlist.getId())
                .name(playlist.getName())
                .build();

        PlaylistDto result = playlistMapper.toDto(playlist);

        assertEquals(playlistDto.getId(), result.getId());
        assertEquals(playlistDto.getName(), result.getName());
    }

    @Test
    public void it_should_convert_list_to_dto() {
        Playlist playlist1 = Playlist.builder()
                .id(1L)
                .name("playlist1")
                .build();
        Playlist playlist2 = Playlist.builder()
                .id(2L)
                .name("playlist2")
                .build();

        List<Playlist> playlists = List.of(playlist1, playlist2);

        PlaylistDto playlistDto1 = PlaylistDto.builder()
                .id(playlist1.getId())
                .name(playlist1.getName())
                .build();
        PlaylistDto playlistDto2 = PlaylistDto.builder()
                .id(playlist2.getId())
                .name(playlist2.getName())
                .build();

        List<PlaylistDto> playlistDtos = List.of(playlistDto1, playlistDto2);

        List<PlaylistDto> result = playlistMapper.listToDto(playlists);

        assertEquals(playlistDtos.get(0).getId(), result.get(0).getId());
        assertEquals(playlistDtos.get(0).getName(), result.get(0).getName());
        assertEquals(playlistDtos.get(1).getId(), result.get(1).getId());
        assertEquals(playlistDtos.get(1).getName(), result.get(1).getName());
    }
}

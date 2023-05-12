package dev.ardijorganxhi.listenify.service;

import dev.ardijorganxhi.listenify.entity.Playlist;
import dev.ardijorganxhi.listenify.entity.Song;
import dev.ardijorganxhi.listenify.entity.User;
import dev.ardijorganxhi.listenify.mapper.PlaylistMapper;
import dev.ardijorganxhi.listenify.mapper.SongMapper;
import dev.ardijorganxhi.listenify.model.PagingResult;
import dev.ardijorganxhi.listenify.model.dto.PlaylistDto;
import dev.ardijorganxhi.listenify.model.dto.SongDto;
import dev.ardijorganxhi.listenify.model.request.PaginationRequest;
import dev.ardijorganxhi.listenify.model.request.PlaylistRequest;
import dev.ardijorganxhi.listenify.repository.PlaylistRepository;
import dev.ardijorganxhi.listenify.repository.SongRepository;
import dev.ardijorganxhi.listenify.repository.UserRepository;
import dev.ardijorganxhi.listenify.utils.PaginationUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class PlaylistServiceTest {

    @Mock
    private PlaylistRepository playlistRepository;

    @Mock
    private PlaylistMapper playlistMapper;

    @Mock
    private SongMapper songMapper;

    @Mock
    private UserRepository userRepository;

    @Mock
    private SongRepository songRepository;

    @InjectMocks
    private PlaylistService playlistService;


    @Test
    public void it_should_find_users_all_playlists() {
        Long userId = 1L;
        PaginationRequest paginationRequest = new PaginationRequest();
        
        User user = User.builder()
                .id(userId)
                .listenifyname("user")
                .build();

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

        Page<Playlist> playlistPage = new PageImpl<>(playlists, PaginationUtils.getPageable(paginationRequest.getPage(), paginationRequest.getSize(), paginationRequest.getDirection(), paginationRequest.getSortField()), playlists.size());

        when(playlistRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(playlistPage);

        when(playlistMapper.toDto(playlist1)).thenReturn(playlistDto1);
        when(playlistMapper.toDto(playlist2)).thenReturn(playlistDto2);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));


        PagingResult<PlaylistDto> result = playlistService.getPlaylists(userId, paginationRequest);

        verify(playlistMapper).toDto(playlist1);
        verify(playlistMapper).toDto(playlist2);

        assertEquals(playlistDtos, result.getContent());
        assertEquals(playlistPage.getTotalPages(), result.getTotalPages());
        assertEquals(playlistPage.getTotalElements(), result.getTotalElements());
        assertEquals(playlistPage.getSize(), result.getSize());
        assertEquals(playlistPage.isEmpty(), result.isEmpty());

    }

    @Test
    public void it_should_create_playlist() {
        Long userId = 1L;
        PlaylistRequest request = PlaylistRequest.builder()
                .name("playlist")
                .build();

        User user = User.builder()
                .id(userId)
                .listenifyname("user")
                .build();

        Playlist playlist = Playlist.builder()
                .name("playlist")
                .build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(playlistMapper.createPlaylist(user, request)).thenReturn(playlist);

        playlistService.createPlaylist(userId, request);

        verify(userRepository).findById(userId);
        verify(playlistMapper).createPlaylist(user, request);
        verify(playlistRepository).save(playlist);

    }


    @Test
    public void it_should_find_by_id() {
        Long playlistId = 1L;
        Long userId = 1L;

        User user = User.builder()
                .id(userId)
                .listenifyname("user")
                .build();

        Playlist playlist = Playlist.builder()
                .id(playlistId)
                .name("playlist")
                .user(user)
                .build();

        PlaylistDto playlistDto = PlaylistDto.builder()
                .id(playlistId)
                .name(playlist.getName())
                .build();

        when(playlistRepository.findByIdAndUserId(playlistId, userId)).thenReturn(Optional.of(playlist));
        when(playlistMapper.toDto(playlist)).thenReturn(playlistDto);

        PlaylistDto result = playlistService.findPlaylistById(playlistId, userId);

        assertEquals(playlistDto.getId(), result.getId());
        assertEquals(playlistDto.getName(), result.getName());

    }

    @Test
    public void it_should_delete_by_id() {
        Long userId = 1L;
        Long playlistId = 1L;

        User user = User.builder()
                .id(userId)
                .listenifyname("user")
                .deleted(false)
                .build();

        Playlist playlist = Playlist.builder()
                .id(playlistId)
                .name("playlist")
                .user(user)
                .deleted(false)
                .build();

        when(playlistRepository.findByIdAndUserId(playlistId, userId)).thenReturn(Optional.of(playlist));

        playlistService.deletePlaylistById(playlistId, userId);

        assertTrue(playlist.isDeleted());
        verify(playlistRepository).save(playlist);
    }

    @Test
    public void it_should_find_songs_of_playlist() {
        Long userId = 1L;
        Long playlistId = 1L;
        PaginationRequest paginationRequest = new PaginationRequest();

        User user = User.builder()
                .id(userId)
                .listenifyname("user")
                .build();

        Playlist playlist = Playlist.builder()
                .id(playlistId)
                .name("playlist")
                .user(user)
                .build();

        Song song1 = Song.builder()
                .id(1L)
                .name("song1")
                .build();

        Song song2 = Song.builder()
                .id(2L)
                .name("song2")
                .build();

        List<Song> songs = List.of(song1, song2);

        SongDto songDto1 = SongDto.builder()
                .id(song1.getId())
                .name(song1.getName())
                .build();
        SongDto songDto2 = SongDto.builder()
                .id(song2.getId())
                .name(song2.getName())
                .build();

        List<SongDto> playlistDtos = List.of(songDto1, songDto2);

        Page<Song> songPage = new PageImpl<>(songs, PaginationUtils.getPageable(paginationRequest.getPage(), paginationRequest.getSize(), paginationRequest.getDirection(), paginationRequest.getSortField()), songs.size());

        when(songRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(songPage);

        when(songMapper.toDto(song1)).thenReturn(songDto1);
        when(songMapper.toDto(song2)).thenReturn(songDto2);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(playlistRepository.findByIdAndUserId(playlistId, userId)).thenReturn(Optional.of(playlist));


        PagingResult<SongDto> result = playlistService.getSongs(playlistId, userId, paginationRequest);

        verify(songMapper).toDto(song1);
        verify(songMapper).toDto(song2);

        assertEquals(playlistDtos, result.getContent());
        assertEquals(songPage.getTotalPages(), result.getTotalPages());
        assertEquals(songPage.getTotalElements(), result.getTotalElements());
        assertEquals(songPage.getSize(), result.getSize());
        assertEquals(songPage.isEmpty(), result.isEmpty());
    }

}

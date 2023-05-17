package dev.ardijorganxhi.listenify.controller;

import dev.ardijorganxhi.listenify.model.PagingResult;
import dev.ardijorganxhi.listenify.model.dto.PlaylistDto;
import dev.ardijorganxhi.listenify.model.dto.SongDto;
import dev.ardijorganxhi.listenify.model.request.PaginationRequest;
import dev.ardijorganxhi.listenify.model.request.PlaylistRequest;
import dev.ardijorganxhi.listenify.service.PlaylistService;
import dev.ardijorganxhi.listenify.service.SongPlaylistService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static dev.ardijorganxhi.listenify.utils.MdcConstant.X_USER_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class PlaylistControllerTest {

    @InjectMocks
    private PlaylistController playlistController;

    @Mock
    private PlaylistService playlistService;

    @Mock
    private SongPlaylistService songPlaylistService;

    @Test
    public void it_should_create_playlist() {
        MDC.put(X_USER_ID, String.valueOf(1L));

        PlaylistRequest request = PlaylistRequest.builder()
                .name("playlist")
                .build();

        playlistController.createPlaylist(request);

        verify(playlistService).createPlaylist(Long.valueOf(MDC.get(X_USER_ID)), request);
    }

    @Test
    public void it_should_get_playlist_by_id() {
        Long playlistId = 1L;
        MDC.put(X_USER_ID, String.valueOf(1L));



        PlaylistDto playlistDto = PlaylistDto.builder()
                .id(playlistId)
                .name("playlist")
                .build();

        when(playlistService.findPlaylistById(playlistId, Long.valueOf(MDC.get(X_USER_ID)))).thenReturn(playlistDto);

        ResponseEntity<PlaylistDto> response = playlistController.findPlaylistById(playlistId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(playlistDto, response.getBody());
    }

    @Test
    public void it_should_delete_playlist_by_id() {
        Long playlistId = 1L;
        MDC.put(X_USER_ID, String.valueOf(1L));

        playlistController.deletePlaylistById(playlistId);

        verify(playlistService).deletePlaylistById(playlistId, Long.valueOf(MDC.get(X_USER_ID)));
    }

    @Test
    public void it_should_add_song_to_playlist() {
        MDC.put(X_USER_ID, String.valueOf(1L));
        Long playlistId = 1L;
        Long songId = 1L;

        playlistController.addSongToPlaylist(playlistId, songId);

        verify(songPlaylistService).addSongToPlaylist(playlistId, songId, Long.valueOf(MDC.get(X_USER_ID)));
    }

    @Test
    public void it_should_delete_song_from_playlist() {
        Long playlistId = 1L;
        Long songId = 1L;

        playlistController.deleteSongFromPlaylist(playlistId, songId);

        verify(songPlaylistService).deleteSongFromPlaylist(playlistId, songId);
    }

    @Test
    public void it_should_get_songs_of_playlist() {
        Long playlistId = 1L;
        MDC.put(X_USER_ID, String.valueOf(1L));

        PaginationRequest request = new PaginationRequest();

        SongDto songDto = SongDto.builder()
                .id(1L)
                .name("song1")
                .build();
        SongDto songDto2 = SongDto.builder()
                .id(2L)
                .name("song2")
                .build();

        List<SongDto> songDtos = List.of(songDto, songDto2);

        PagingResult<SongDto> pagingResult = new PagingResult<>();
        pagingResult.setContent(songDtos);
        pagingResult.setTotalPages(1);

        when(playlistService.getSongs(playlistId, Long.valueOf(MDC.get(X_USER_ID)), request)).thenReturn(pagingResult);

        ResponseEntity<PagingResult<SongDto>> response = playlistController.getSongs(playlistId, request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pagingResult, response.getBody());
    }

}

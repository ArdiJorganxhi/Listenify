package dev.ardijorganxhi.listenify.controller;

import dev.ardijorganxhi.listenify.model.PagingResult;
import dev.ardijorganxhi.listenify.model.dto.AlbumDto;
import dev.ardijorganxhi.listenify.model.dto.SongDto;
import dev.ardijorganxhi.listenify.model.request.PaginationRequest;
import dev.ardijorganxhi.listenify.service.AlbumService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class AlbumControllerTest {

    @InjectMocks
    private AlbumController albumController;

    @Mock
    private AlbumService albumService;

    @Test
    public void it_should_get_album_by_id() {
        Long albumId = 1L;

        AlbumDto albumDto = AlbumDto.builder()
                .id(albumId)
                .name("album")
                .build();

        when(albumService.getAlbumById(albumId)).thenReturn(albumDto);

        ResponseEntity<AlbumDto> response = albumController.getAlbumById(albumId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(albumDto, response.getBody());
    }

    @Test
    public void it_should_delete_album_by_id() {
        Long albumId = 1L;

        albumController.deleteAlbumById(albumId);

        verify(albumService).deleteAlbumById(albumId);
    }

    @Test
    public void it_should_add_song_to_album() {
        Long albumId = 1L;
        Long songId = 1L;

        albumController.addSongsToAlbum(albumId, songId);

        verify(albumService).addSongsToAlbum(albumId, songId);
    }

    @Test
    public void it_should_get_songs_of_album() {
        Long albumId = 1L;

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

        when(albumService.getSongsOfAlbum(albumId, request)).thenReturn(pagingResult);

        ResponseEntity<PagingResult<SongDto>> response = albumController.getSongsOfAlbum(albumId, request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pagingResult, response.getBody());
    }
}

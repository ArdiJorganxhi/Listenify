package dev.ardijorganxhi.listenify.controller;

import dev.ardijorganxhi.listenify.model.PagingResult;
import dev.ardijorganxhi.listenify.model.dto.SongDto;
import dev.ardijorganxhi.listenify.model.request.PaginationRequest;
import dev.ardijorganxhi.listenify.service.SongService;
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
public class SongControllerTest {

    @InjectMocks
    private SongController songController;

    @Mock
    private SongService songService;

    @Test
    public void it_should_find_all_songs() {
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

        when(songService.findAllSongs("", request)).thenReturn(pagingResult);

        ResponseEntity<PagingResult<SongDto>> response = songController.findAllSongs("", request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pagingResult, response.getBody());
    }

    @Test
    public void it_should_find_song_by_id() throws Exception {
        Long songId = 1L;

        SongDto songDto = SongDto.builder()
                .id(songId)
                .name("song")
                .build();

        when(songService.findSongById(songId)).thenReturn(songDto);

        ResponseEntity<SongDto> response = songController.findSongById(songId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(songDto, response.getBody());
    }

    @Test
    public void it_should_delete_song_by_id() {
        Long songId = 1L;

        songController.deleteById(songId);

        verify(songService).deleteById(songId);
    }
}

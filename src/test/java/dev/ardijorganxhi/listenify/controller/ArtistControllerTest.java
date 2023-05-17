package dev.ardijorganxhi.listenify.controller;

import dev.ardijorganxhi.listenify.entity.Artist;
import dev.ardijorganxhi.listenify.model.PagingResult;
import dev.ardijorganxhi.listenify.model.dto.AlbumDto;
import dev.ardijorganxhi.listenify.model.dto.ArtistDto;
import dev.ardijorganxhi.listenify.model.dto.SongDto;
import dev.ardijorganxhi.listenify.model.request.AlbumRequest;
import dev.ardijorganxhi.listenify.model.request.ArtistRequest;
import dev.ardijorganxhi.listenify.model.request.PaginationRequest;
import dev.ardijorganxhi.listenify.model.request.SongRequest;
import dev.ardijorganxhi.listenify.service.AlbumService;
import dev.ardijorganxhi.listenify.service.ArtistService;
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
public class ArtistControllerTest {

    @InjectMocks
    private ArtistController artistController;

    @Mock
    private ArtistService artistService;

    @Mock
    private SongService songService;

    @Mock
    private AlbumService albumService;

    @Test
    public void it_should_find_all_artists() {

        PaginationRequest request = new PaginationRequest();

        ArtistDto artistDto = ArtistDto.builder()
                .id(1L)
                .listenifyname("artist1")
                .build();
        ArtistDto artistDto2 = ArtistDto.builder()
                .id(2L)
                .listenifyname("artist2")
                .build();

        List<ArtistDto> albumDtos = List.of(artistDto, artistDto2);

        PagingResult<ArtistDto> pagingResult = new PagingResult<>();
        pagingResult.setContent(albumDtos);
        pagingResult.setTotalPages(1);

        when(artistService.findAllArtists("", request)).thenReturn(pagingResult);

        ResponseEntity<PagingResult<ArtistDto>> response = artistController.findAllArtists("", request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pagingResult, response.getBody());
    }

    @Test
    public void it_should_register_artist() {
        ArtistRequest request = ArtistRequest.builder()
                .listenifyname("artist")
                .build();

        artistController.registerArtist(request);

        verify(artistService).registerArtist(request);

    }

    @Test
    public void it_should_find_artist_by_id() throws Exception {
        Long artistId = 1L;

        ArtistDto artistDto = ArtistDto.builder()
                .id(artistId)
                .listenifyname("artist")
                .build();

        when(artistService.findById(artistId)).thenReturn(artistDto);

        ResponseEntity<ArtistDto> response = artistController.getArtist(artistId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(artistDto, response.getBody());
    }

    @Test
    public void it_should_delete_artist_by_id() {
        Long artistId = 1L;

        artistController.deleteArtistById(artistId);

        verify(artistService).deleteById(artistId);
    }

    @Test
    public void it_should_register_song() {
        Long artistId = 1L;
        SongRequest request = SongRequest.builder()
                .name("song")
                .genre("genre")
                .build();

        artistController.registerSong(request, artistId);

        verify(songService).registerSong(request, artistId);

    }

    @Test
    public void it_should_get_songs_by_artistid() {
        Long artistId = 1L;

        PaginationRequest request = new PaginationRequest();

        ArtistDto artist = ArtistDto.builder()
                .id(artistId)
                .listenifyname("artist")
                .build();

        SongDto songDto = SongDto.builder()
                .id(1L)
                .name("song1")
                .artist(artist)
                .build();
        SongDto songDto2 = SongDto.builder()
                .id(2L)
                .name("song2")
                .artist(artist)
                .build();

        List<SongDto> songDtos = List.of(songDto, songDto2);

        PagingResult<SongDto> pagingResult = new PagingResult<>();
        pagingResult.setContent(songDtos);
        pagingResult.setTotalPages(1);

        when(artistService.findSongsByArtistId(artistId, request)).thenReturn(pagingResult);

        ResponseEntity<PagingResult<SongDto>> response = artistController.getSongsByArtistId(artistId, request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pagingResult, response.getBody());
    }

    @Test
    public void it_should_create_album() {
        Long artistId = 1L;

        AlbumRequest request = AlbumRequest.builder()
                .name("album")
                .build();

        artistController.createAlbum(request, artistId);

        verify(albumService).createAlbum(request, artistId);
    }

    @Test
    public void it_should_get_albums_by_artist() {
        Long artistId = 1L;

        PaginationRequest request = new PaginationRequest();

        AlbumDto albumDto = AlbumDto.builder()
                .id(1L)
                .name("album1")
                .build();
        AlbumDto albumDto2 = AlbumDto.builder()
                .id(2L)
                .name("song2")
                .build();

        List<AlbumDto> albumDtos = List.of(albumDto, albumDto2);

        PagingResult<AlbumDto> pagingResult = new PagingResult<>();
        pagingResult.setContent(albumDtos);
        pagingResult.setTotalPages(1);

        when(albumService.getAlbumsByArtist(artistId, request)).thenReturn(pagingResult);

        ResponseEntity<PagingResult<AlbumDto>> response = artistController.getAlbumsByArtist(artistId, request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pagingResult, response.getBody());
    }

}

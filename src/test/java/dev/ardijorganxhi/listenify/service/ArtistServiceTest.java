package dev.ardijorganxhi.listenify.service;

import dev.ardijorganxhi.listenify.entity.Artist;
import dev.ardijorganxhi.listenify.entity.Song;
import dev.ardijorganxhi.listenify.mapper.ArtistMapper;
import dev.ardijorganxhi.listenify.mapper.SongMapper;
import dev.ardijorganxhi.listenify.model.PagingResult;
import dev.ardijorganxhi.listenify.model.dto.ArtistDto;
import dev.ardijorganxhi.listenify.model.dto.SongDto;
import dev.ardijorganxhi.listenify.model.request.PaginationRequest;
import dev.ardijorganxhi.listenify.repository.ArtistRepository;
import dev.ardijorganxhi.listenify.repository.ArtistSpecification;
import dev.ardijorganxhi.listenify.repository.SongRepository;
import dev.ardijorganxhi.listenify.utils.PaginationUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ArtistServiceTest {

    @Mock
    private ArtistRepository artistRepository;

    @Mock
    private SongRepository songRepository;

    @Mock
    private ArtistMapper artistMapper;

    @Mock
    private SongMapper songMapper;

    @InjectMocks
    private ArtistService artistService;


    @Test
    public void it_should_find_all_artists() {

        PaginationRequest paginationRequest = PaginationRequest.builder()
                .page(1)
                .size(10)
                .direction(Sort.Direction.ASC)
                .sortField("id")
                .build();

        Artist artist1 = Artist.builder()
                .id(1L)
                .listenifyname("artist1")
                .build();
        Artist artist2 = Artist.builder()
                .id(2L)
                .listenifyname("user2")
                .build();

        List<Artist> artists = List.of(artist1, artist2);

        ArtistDto artistDto1 = ArtistDto.builder()
                .id(artist1.getId())
                .listenifyname(artist1.getListenifyname())
                .build();
        ArtistDto artistDto2 = ArtistDto.builder()
                .id(artist2.getId())
                .listenifyname(artist2.getListenifyname())
                .build();

        List<ArtistDto> artistDtos = List.of(artistDto1, artistDto2);

        Page<Artist> artistPage = new PageImpl<>(artists, PaginationUtils.getPageable(paginationRequest.getPage(), paginationRequest.getSize(), paginationRequest.getDirection(), paginationRequest.getSortField()), artists.size());

        when(artistRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(artistPage);

        when(artistMapper.toDto(artist1)).thenReturn(artistDto1);
        when(artistMapper.toDto(artist2)).thenReturn(artistDto2);

        PagingResult<ArtistDto> result = artistService.findAllArtists("", paginationRequest);

        verify(artistMapper).toDto(artist1);
        verify(artistMapper).toDto(artist2);

        assertEquals(artistDtos, result.getContent());
        assertEquals(artistPage.getTotalPages(), result.getTotalPages());
        assertEquals(artistPage.getTotalElements(), result.getTotalElements());
        assertEquals(artistPage.getSize(), result.getSize());
        assertEquals(artistPage.isEmpty(), result.isEmpty());
    }

    @Test
    public void it_should_get_songs_by_artist_id() {
        Long artistId = 1L;
        PaginationRequest paginationRequest = PaginationRequest.builder()
                .page(1)
                .size(10)
                .direction(Sort.Direction.ASC)
                .sortField("id")
                .build();

        Artist artist = Artist.builder()
                .id(artistId)
                .listenifyname("artist1")
                .build();

        Song song1 = Song.builder()
                .id(1L)
                .name("song1")
                .artist(artist)
                .build();
        Song song2 = Song.builder()
                .id(2L)
                .name("song2")
                .artist(artist)
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

        List<SongDto> songDtos = List.of(songDto1, songDto2);

        Page<Song> songPage = new PageImpl<>(songs, PaginationUtils.getPageable(paginationRequest.getPage(), paginationRequest.getSize(), paginationRequest.getDirection(), paginationRequest.getSortField()), songs.size());

        when(songRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(songPage);

        when(songMapper.toDto(song1)).thenReturn(songDto1);
        when(songMapper.toDto(song2)).thenReturn(songDto2);

        PagingResult<SongDto> result = artistService.findSongsByArtistId(artistId, paginationRequest);

        verify(songMapper).toDto(song1);
        verify(songMapper).toDto(song2);


        assertEquals(songDtos, result.getContent());
        assertEquals(songPage.getTotalPages(), result.getTotalPages());
        assertEquals(songPage.getTotalElements(), result.getTotalElements());
        assertEquals(songPage.getSize(), result.getSize());
        assertEquals(songPage.isEmpty(), result.isEmpty());
    }

    @Test
    public void it_should_find_by_id() throws Exception {

        Artist artist = Artist.builder()
                .id(1L)
                .listenifyname("artist1")
                .build();

        ArtistDto artistDto = ArtistDto.builder()
                .id(1L)
                .listenifyname("artist1")
                .build();

        when(artistRepository.findById(1L)).thenReturn(Optional.of(artist));
        when(artistMapper.toDto(artist)).thenReturn(artistDto);

        ArtistDto result = artistService.findById(1L);

        assertEquals(artistDto.getId(), result.getId());
        assertEquals(artistDto.getListenifyname(), result.getListenifyname());

        verify(artistRepository).findById(1L);
        verify(artistMapper).toDto(artist);
    }

    @Test
    public void it_should_delete_by_id() {
        Artist artist = Artist.builder()
                .id(1L)
                .listenifyname("artist1")
                .build();

        when(artistRepository.findById(1L)).thenReturn(Optional.of(artist));

        artistService.deleteById(1L);

        assertTrue(artist.isDeleted());
        verify(artistRepository).save(artist);
    }
}

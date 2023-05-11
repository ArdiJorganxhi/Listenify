package dev.ardijorganxhi.listenify.service;

import dev.ardijorganxhi.listenify.entity.Artist;
import dev.ardijorganxhi.listenify.entity.Song;
import dev.ardijorganxhi.listenify.mapper.SongMapper;
import dev.ardijorganxhi.listenify.model.PagingResult;
import dev.ardijorganxhi.listenify.model.dto.ArtistDto;
import dev.ardijorganxhi.listenify.model.dto.SongDto;
import dev.ardijorganxhi.listenify.model.request.PaginationRequest;
import dev.ardijorganxhi.listenify.model.request.SongRequest;
import dev.ardijorganxhi.listenify.repository.ArtistRepository;
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
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SongServiceTest {

    @Mock
    private SongRepository songRepository;

    @Mock
    private ArtistRepository artistRepository;

    @Mock
    private SongMapper songMapper;

    @InjectMocks
    private SongService songService;
    
    
    @Test
    public void it_should_find_all_songs() {
        PaginationRequest paginationRequest = new PaginationRequest();

        Song song1 = Song.builder()
                .id(1L)
                .name("song1")
                .build();
        Song song2 = Song.builder()
                .id(2L)
                .name("user2")
                .build();

        List<Song> artists = List.of(song1, song2);

        SongDto songDto1 = SongDto.builder()
                .id(song1.getId())
                .name(song1.getName())
                .build();
        SongDto songDto2 = SongDto.builder()
                .id(song2.getId())
                .name(song2.getName())
                .build();

        List<SongDto> artistDtos = List.of(songDto1, songDto2);

        Page<Song> songPage = new PageImpl<>(artists, PaginationUtils.getPageable(paginationRequest.getPage(), paginationRequest.getSize(), paginationRequest.getDirection(), paginationRequest.getSortField()), artists.size());

        when(songRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(songPage);

        when(songMapper.toDto(song1)).thenReturn(songDto1);
        when(songMapper.toDto(song2)).thenReturn(songDto2);

        PagingResult<SongDto> result = songService.findAllSongs("", paginationRequest);

        verify(songMapper).toDto(song1);
        verify(songMapper).toDto(song2);

        assertEquals(artistDtos, result.getContent());
        assertEquals(songPage.getTotalPages(), result.getTotalPages());
        assertEquals(songPage.getTotalElements(), result.getTotalElements());
        assertEquals(songPage.getSize(), result.getSize());
        assertEquals(songPage.isEmpty(), result.isEmpty());
    }

    @Test
    public void it_should_register_song() {
        Long artistId = 1L;

        Artist artist = Artist.builder()
                .id(artistId)
                .listenifyname("user1")
                .build();

        when(artistRepository.findById(artistId)).thenReturn(Optional.of(artist));

        SongRequest songRequest = SongRequest.builder()
                .name("song1")
                .genre("genre")
                .build();

        Song song = Song.builder()
                .id(1L)
                .name("song1")
                .artist(artist)
                .build();

        when(songMapper.registerSong(songRequest, artist)).thenReturn(song);

        songService.registerSong(songRequest, artistId);

        verify(artistRepository).findById(artistId);
        verify(songMapper).registerSong(songRequest, artist);
        verify(songRepository).save(song);
    }

    @Test
    public void it_should_find_by_id() throws Exception {
        Long songId = 1L;

        Song song = Song.builder()
                .id(songId)
                .name("song1")
                .genre("genre")
                .build();

        SongDto songDto = SongDto.builder()
                .id(songId)
                .name("song1")
                .build();

        when(songRepository.findById(songId)).thenReturn(Optional.of(song));
        when(songMapper.toDto(song)).thenReturn(songDto);

        SongDto result = songService.findSongById(songId);

        assertEquals(songDto.getId(), result.getId());
        assertEquals(songDto.getName(), result.getName());

        verify(songRepository).findById(songId);
        verify(songMapper).toDto(song);
    }

    @Test
    public void it_should_delete_by_id() {
        Long songId = 1L;

        Song song = Song.builder()
                .id(songId)
                .name("song1")
                .genre("genre")
                .deleted(false)
                .build();

        when(songRepository.findById(songId)).thenReturn(Optional.of(song));

        songService.deleteById(songId);

        assertTrue(song.isDeleted());
        verify(songRepository).save(song);
    }


}

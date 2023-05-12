package dev.ardijorganxhi.listenify.service;

import dev.ardijorganxhi.listenify.entity.Album;
import dev.ardijorganxhi.listenify.entity.Artist;
import dev.ardijorganxhi.listenify.entity.Song;
import dev.ardijorganxhi.listenify.mapper.AlbumMapper;
import dev.ardijorganxhi.listenify.mapper.SongMapper;
import dev.ardijorganxhi.listenify.model.PagingResult;
import dev.ardijorganxhi.listenify.model.dto.AlbumDto;
import dev.ardijorganxhi.listenify.model.dto.SongDto;
import dev.ardijorganxhi.listenify.model.request.AlbumRequest;
import dev.ardijorganxhi.listenify.model.request.PaginationRequest;
import dev.ardijorganxhi.listenify.repository.AlbumRepository;
import dev.ardijorganxhi.listenify.repository.ArtistRepository;
import dev.ardijorganxhi.listenify.repository.SongRepository;
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
public class AlbumServiceTest {

    @Mock
    private AlbumRepository albumRepository;

    @Mock
    private ArtistRepository artistRepository;

    @Mock
    private SongRepository songRepository;

    @Mock
    private SongMapper songMapper;

    @Mock
    private AlbumMapper albumMapper;

    @InjectMocks
    private AlbumService albumService;


    @Test
    public void it_should_find_artist_all_albums() {
        Long artistId = 1L;
        PaginationRequest paginationRequest = new PaginationRequest();

        Artist artist = Artist.builder()
                .id(artistId)
                .listenifyname("artist")
                .build();

        Album album1 = Album.builder()
                .id(1L)
                .name("album1")
                .artist(artist)
                .build();
        Album album2 = Album.builder()
                .id(2L)
                .name("album2")
                .artist(artist)
                .build();

        List<Album> albums = List.of(album1, album2);

        AlbumDto albumDto1 = AlbumDto.builder()
                .id(album1.getId())
                .name(album1.getName())
                .build();
        AlbumDto albumDto2 = AlbumDto.builder()
                .id(album2.getId())
                .name(album2.getName())
                .build();

        List<AlbumDto> albumDtos = List.of(albumDto1, albumDto2);

        Page<Album> albumPage = new PageImpl<>(albums, PaginationUtils.getPageable(paginationRequest.getPage(), paginationRequest.getSize(), paginationRequest.getDirection(), paginationRequest.getSortField()), albums.size());

        when(albumRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(albumPage);

        when(albumMapper.toDto(album1)).thenReturn(albumDto1);
        when(albumMapper.toDto(album2)).thenReturn(albumDto2);
        when(artistRepository.findById(artistId)).thenReturn(Optional.of(artist));


        PagingResult<AlbumDto> result = albumService.getAlbumsByArtist(artistId, paginationRequest);

        verify(albumMapper).toDto(album1);
        verify(albumMapper).toDto(album2);

        assertEquals(albumDtos, result.getContent());
        assertEquals(albumPage.getTotalPages(), result.getTotalPages());
        assertEquals(albumPage.getTotalElements(), result.getTotalElements());
        assertEquals(albumPage.getSize(), result.getSize());
        assertEquals(albumPage.isEmpty(), result.isEmpty());
    }

    @Test
    public void it_should_get_all_songs_of_album() {
        Long albumId = 1L;
        PaginationRequest paginationRequest = new PaginationRequest();

        Album album = Album.builder()
                .id(albumId)
                .name("album")
                .build();

        Song song1 = Song.builder()
                .id(1L)
                .name("song1")
                .album(album)

                .build();
        Song song2 = Song.builder()
                .id(2L)
                .name("song2")
                .album(album)
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

        PagingResult<SongDto> result = albumService.getSongsOfAlbum(albumId, paginationRequest);

        verify(songMapper).toDto(song1);
        verify(songMapper).toDto(song2);

        assertEquals(songDtos, result.getContent());
        assertEquals(songPage.getTotalPages(), result.getTotalPages());
        assertEquals(songPage.getTotalElements(), result.getTotalElements());
        assertEquals(songPage.getSize(), result.getSize());
        assertEquals(songPage.isEmpty(), result.isEmpty());
    }

    @Test
    public void it_should_create_album() {
        Long artistId = 1L;

        Artist artist = Artist.builder()
                .id(artistId)
                .listenifyname("artist")
                .build();
        AlbumRequest request = AlbumRequest.builder()
                .name("album")
                .build();

        Album album = Album.builder()
                .name("album")
                .artist(artist)
                .build();

        when(artistRepository.findById(artistId)).thenReturn(Optional.of(artist));
        when(albumMapper.createAlbum(request, artist)).thenReturn(album);

        albumService.createAlbum(request, artistId);

        verify(artistRepository).findById(artistId);
        verify(albumMapper).createAlbum(request, artist);
        verify(albumRepository).save(album);
    }

    @Test
    public void it_should_find_by_id() {
        Long albumId = 1L;

        Album album = Album.builder()
                .id(albumId)
                .name("album")
                .build();

        AlbumDto albumDto = AlbumDto.builder()
                .name("album")
                .build();

        when(albumRepository.findById(albumId)).thenReturn(Optional.of(album));
        when(albumMapper.toDto(album)).thenReturn(albumDto);

        AlbumDto result = albumService.getAlbumById(albumId);

        assertEquals(albumDto.getId(), result.getId());
        assertEquals(albumDto.getName(), result.getName());
    }

    @Test
    public void it_should_delete_by_id() {
        Long albumId = 1L;

        Album album = Album.builder()
                .id(albumId)
                .name("album")
                .deleted(false)
                .build();

        when(albumRepository.findById(albumId)).thenReturn(Optional.of(album));

        albumService.deleteAlbumById(albumId);

        assertTrue(album.isDeleted());
        verify(albumRepository).save(album);
    }
}

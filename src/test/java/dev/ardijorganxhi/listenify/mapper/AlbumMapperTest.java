package dev.ardijorganxhi.listenify.mapper;

import dev.ardijorganxhi.listenify.entity.Album;
import dev.ardijorganxhi.listenify.entity.Artist;
import dev.ardijorganxhi.listenify.model.dto.AlbumDto;
import dev.ardijorganxhi.listenify.model.request.AlbumRequest;
import dev.ardijorganxhi.listenify.model.request.ArtistRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class AlbumMapperTest {

    @InjectMocks
    private AlbumMapper albumMapper;

    @Test
    public void it_should_register() {
        Artist artist = Artist.builder()
                .id(1L)
                .listenifyname("artist")
                .build();

        AlbumRequest request = AlbumRequest.builder()
                .name("album")
                .build();

        Album album = Album.builder()
                .name(request.getName())
                .artist(artist)
                .build();

        Album result = albumMapper.createAlbum(request, artist);

        assertEquals(album.getName(), result.getName());
        assertEquals(album.getArtist(), result.getArtist());
    }

    @Test
    public void it_should_convert_to_dto() {
        Album album = Album.builder()
                .id(1L)
                .name("album")
                .build();

        AlbumDto albumDto = AlbumDto.builder()
                .id(album.getId())
                .name(album.getName())
                .build();

        AlbumDto result = albumMapper.toDto(album);

        assertEquals(albumDto.getId(), result.getId());
        assertEquals(albumDto.getName(), result.getName());
    }

    @Test
    public void it_should_convert_list_to_dto() {
        Album album1 = Album.builder()
                .id(1L)
                .name("album1")
                .build();
        Album album2 = Album.builder()
                .id(2L)
                .name("album2")
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

        List<AlbumDto> result = albumMapper.listToDto(albums);

        assertEquals(albumDtos.get(0).getId(), result.get(0).getId());
        assertEquals(albumDtos.get(0).getName(), result.get(0).getName());
        assertEquals(albumDtos.get(1).getId(), result.get(1).getId());
        assertEquals(albumDtos.get(1).getName(), result.get(1).getName());
    }
}

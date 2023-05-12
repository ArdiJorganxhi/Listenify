package dev.ardijorganxhi.listenify.mapper;

import dev.ardijorganxhi.listenify.entity.Artist;
import dev.ardijorganxhi.listenify.model.dto.ArtistDto;
import dev.ardijorganxhi.listenify.model.request.ArtistRequest;
import dev.ardijorganxhi.listenify.repository.ArtistRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ArtistMapperTest {

    @InjectMocks
    private ArtistMapper artistMapper;

    @Test
    public void it_should_register_artist() {
        ArtistRequest request = ArtistRequest.builder()
                .listenifyname("artist")
                .build();

        Artist artist = Artist.builder()
                .listenifyname(request.getListenifyname())
                .build();

        Artist result = artistMapper.registerArtist(request);

        assertEquals(artist.getListenifyname(), result.getListenifyname());
    }

    @Test
    public void it_should_convert_to_dto() {
        Artist artist = Artist.builder()
                .id(1L)
                .listenifyname("artist")
                .build();

        ArtistDto artistDto = ArtistDto.builder()
                .id(artist.getId())
                .listenifyname(artist.getListenifyname())
                .build();

        ArtistDto result = artistMapper.toDto(artist);

        assertEquals(artistDto.getId(), result.getId());
        assertEquals(artistDto.getListenifyname(), result.getListenifyname());
    }

    @Test
    public void it_should_convert_list_to_dto() {
        Artist artist1 = Artist.builder()
                .id(1L)
                .listenifyname("artist1")
                .build();
        Artist artist2 = Artist.builder()
                .id(2L)
                .listenifyname("artist2")
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

        List<ArtistDto> result = artistMapper.listToDto(artists);

        assertEquals(artistDtos.get(0).getId(), result.get(0).getId());
        assertEquals(artistDtos.get(0).getListenifyname(), result.get(0).getListenifyname());
        assertEquals(artistDtos.get(1).getId(), result.get(1).getId());
        assertEquals(artistDtos.get(1).getListenifyname(), result.get(1).getListenifyname());
    }


}

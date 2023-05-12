package dev.ardijorganxhi.listenify.mapper;

import dev.ardijorganxhi.listenify.entity.Artist;
import dev.ardijorganxhi.listenify.entity.Song;
import dev.ardijorganxhi.listenify.model.dto.ArtistDto;
import dev.ardijorganxhi.listenify.model.dto.SongDto;
import dev.ardijorganxhi.listenify.model.request.SongRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SongMapperTest {

    @Mock
    private ArtistMapper artistMapper;

    @InjectMocks
    private SongMapper songMapper;


    @Test
    public void it_should_register_song() {
        Artist artist = Artist.builder()
                .id(1L)
                .listenifyname("artist")
                .build();

        SongRequest request = SongRequest.builder()
                .name("song")
                .genre("genre")
                .build();

        Song song = Song.builder()
                .name(request.getName())
                .genre(request.getGenre())
                .artist(artist)
                .build();

        Song result = songMapper.registerSong(request, artist);

        assertEquals(song.getName(), result.getName());
        assertEquals(song.getGenre(), result.getGenre());
        assertEquals(song.getArtist(), result.getArtist());
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

        when(artistMapper.toDto(artist)).thenReturn(artistDto);

        Song song = Song.builder()
                .name("song")
                .artist(artist)
                .build();

        SongDto songDto = SongDto.builder()
                .name(song.getName())
                .artist(artistDto)
                .build();

        SongDto result = songMapper.toDto(song);

        assertEquals(songDto.getName(), result.getName());
        verify(artistMapper).toDto(artist);
    }

    @Test
    public void it_should_convert_list_to_dto() {
        Artist artist = Artist.builder()
                .id(1L)
                .listenifyname("artist")
                .build();

        ArtistDto artistDto = ArtistDto.builder()
                .id(artist.getId())
                .listenifyname(artist.getListenifyname())
                .build();

        when(artistMapper.toDto(artist)).thenReturn(artistDto);

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
                .artist(artistDto)
                .build();
        SongDto songDto2 = SongDto.builder()
                .id(song2.getId())
                .name(song2.getName())
                .artist(artistDto)
                .build();

        List<SongDto> songDtos = List.of(songDto1, songDto2);

        List<SongDto> result = songMapper.listToDto(songs);

        assertEquals(songDtos.get(0).getId(), result.get(0).getId());
        assertEquals(songDtos.get(0).getName(), result.get(0).getName());
        assertEquals(songDtos.get(1).getId(), result.get(1).getId());
        assertEquals(songDtos.get(1).getName(), result.get(1).getName());
        verify(artistMapper, times(2)).toDto(artist);


    }
}

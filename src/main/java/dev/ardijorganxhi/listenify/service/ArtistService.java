package dev.ardijorganxhi.listenify.service;

import dev.ardijorganxhi.listenify.entity.Artist;
import dev.ardijorganxhi.listenify.entity.Song;
import dev.ardijorganxhi.listenify.entity.User;
import dev.ardijorganxhi.listenify.mapper.ArtistMapper;
import dev.ardijorganxhi.listenify.mapper.SongMapper;
import dev.ardijorganxhi.listenify.model.PagingResult;
import dev.ardijorganxhi.listenify.model.dto.ArtistDto;
import dev.ardijorganxhi.listenify.model.dto.SongDto;
import dev.ardijorganxhi.listenify.model.dto.UserDto;
import dev.ardijorganxhi.listenify.model.request.ArtistRequest;
import dev.ardijorganxhi.listenify.model.request.PaginationRequest;
import dev.ardijorganxhi.listenify.repository.ArtistRepository;
import dev.ardijorganxhi.listenify.repository.ArtistSpecification;
import dev.ardijorganxhi.listenify.repository.SongRepository;
import dev.ardijorganxhi.listenify.utils.PaginationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArtistService {

    private final ArtistRepository artistRepository;
    private final ArtistMapper artistMapper;
    private final SongRepository songRepository;
    private final SongMapper songMapper;

    public PagingResult<ArtistDto> findAllArtists(PaginationRequest request) {
        final Pageable pageable = PaginationUtils.getPageable(request.getPage(), request.getSize(), request.getDirection(), request.getSortField());
        final Specification<Artist> specification = ArtistSpecification.getArtists();
        final Page<Artist> artistPage = artistRepository.findAll(specification, pageable);
        final List<ArtistDto> artists = artistPage.stream().map(artistMapper::toDto).toList();
        return new PagingResult<>(artists,
                artistPage.getTotalPages(),
                artistPage.getTotalElements(),
                artistPage.getSize(),
                artistPage.getNumber(),
                artistPage.isEmpty());

    }

    public PagingResult<SongDto> findSongsByArtistId(Long artistId, PaginationRequest request) {
        final Pageable pageable = PaginationUtils.getPageable(request.getPage(), request.getSize(), request.getDirection(), request.getSortField());
        final Specification<Song> specification = ArtistSpecification.getSongsByArtistId(artistId);
        final Page<Song> songPage = songRepository.findAll(specification, pageable);
        final List<SongDto> songs = songPage.stream().map(songMapper::toDto).toList();
        return new PagingResult<>(songs,
                songPage.getTotalPages(),
                songPage.getTotalElements(),
                songPage.getSize(),
                songPage.getNumber(),
                songPage.isEmpty());

    }
    public void registerArtist(ArtistRequest request) {
        Artist artist = artistMapper.registerArtist(request);
        artistRepository.save(artist);
    }

    public ArtistDto findById(Long id) throws Exception {
        Artist artist = artistRepository.findById(id).orElseThrow();
        if(!artist.isDeleted()){
            return artistMapper.toDto(artist);
        } else {
            throw new Exception("Artist not found!");
        }

    }

    public void deleteById(Long id) {
        Artist artist = artistRepository.findById(id).orElseThrow();
        artist.setDeleted(true);
        artistRepository.save(artist);
    }
}

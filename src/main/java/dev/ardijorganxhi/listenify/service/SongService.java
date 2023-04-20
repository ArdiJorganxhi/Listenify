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
public class SongService {

    private final SongRepository songRepository;
    private final ArtistRepository artistRepository;
    private final SongMapper songMapper;

    public PagingResult<SongDto> findAllSongs(PaginationRequest request) {
        final Pageable pageable = PaginationUtils.getPageable(request.getPage(), request.getSize(), request.getDirection(), request.getSortField());
        final Specification<Artist> specification = ArtistSpecification.getArtists();
        final Page<Song> songPage = songRepository.findAll(specification, pageable);
        final List<SongDto> artists = songPage.stream().map(songMapper::toDto).toList();
        return new PagingResult<>(artists,
                songPage.getTotalPages(),
                songPage.getTotalElements(),
                songPage.getSize(),
                songPage.getNumber(),
                songPage.isEmpty());

    }

    public void registerSong(SongRequest request, Long artistId) {
        Artist artist = artistRepository.findById(artistId).orElseThrow();
        Song song = songMapper.registerSong(request, artist);
        songRepository.save(song);
    }

    public SongDto findSongById(Long id) throws Exception {
        Song song = songRepository.findById(id).orElseThrow();
        if(!song.isDeleted()){
            return songMapper.toDto(song);
        } else {
            throw new Exception("Song not found!");
        }

    }

    public void deleteById(Long id) {
        Song song = songRepository.findById(id).orElseThrow();
        song.setDeleted(true);
        songRepository.save(song);
    }

}

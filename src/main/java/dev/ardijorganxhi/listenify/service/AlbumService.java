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
import dev.ardijorganxhi.listenify.repository.AlbumSpecification;
import dev.ardijorganxhi.listenify.repository.ArtistRepository;
import dev.ardijorganxhi.listenify.repository.SongRepository;
import dev.ardijorganxhi.listenify.utils.PaginationUtils;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlbumService {

    private final AlbumRepository albumRepository;
    private final AlbumMapper albumMapper;
    private final ArtistRepository artistRepository;
    private final SongRepository songRepository;

    private final SongMapper songMapper;


    public PagingResult<AlbumDto> getAlbumsByArtist(Long artistId, PaginationRequest request) {
        final Pageable pageable = PaginationUtils.getPageable(request.getPage(), request.getSize(), request.getDirection(), request.getSortField());
        final Specification<Album> specification = AlbumSpecification.getAlbumsByArtist(artistId);
        final Page<Album> albumPage = albumRepository.findAll(specification, pageable);
        final List<AlbumDto> albums = albumPage.stream().map(albumMapper::toDto).toList();
        return new PagingResult<>(albums,
                albumPage.getTotalPages(),
                albumPage.getTotalElements(),
                albumPage.getSize(),
                albumPage.getNumber(),
                albumPage.isEmpty());
    }

    public PagingResult<SongDto> getSongsOfAlbum(Long albumId, PaginationRequest request) {
        final Pageable pageable = PaginationUtils.getPageable(request.getPage(), request.getSize(), request.getDirection(), request.getSortField());
        final Specification<Song> specification = AlbumSpecification.getSongsOfAlbum(albumId);
        final Page<Song> songPage = songRepository.findAll(specification, pageable);
        final List<SongDto> songs = songPage.stream().map(songMapper::toDto).toList();
        return new PagingResult<>(songs,
                songPage.getTotalPages(),
                songPage.getTotalElements(),
                songPage.getSize(),
                songPage.getNumber(),
                songPage.isEmpty());
    }

    public void createAlbum(AlbumRequest request, Long artistId) {
        Artist artist = artistRepository.findById(artistId).orElseThrow();
        Album album = albumMapper.createAlbum(request, artist);
        albumRepository.save(album);
    }

    public AlbumDto getAlbumById(Long id) {
        Album album = albumRepository.findById(id).orElseThrow();
        return albumMapper.toDto(album);
    }

    public void deleteAlbumById(Long id){
        Album album = albumRepository.findById(id).orElseThrow();
        album.setDeleted(true);
        albumRepository.save(album);
    }

    public void addSongsToAlbum(Long albumId, Long songId) {
        Album album = albumRepository.findById(albumId).orElseThrow();
        Song song = songRepository.findById(songId).orElseThrow();
        song.setAlbum(album);

    }
}

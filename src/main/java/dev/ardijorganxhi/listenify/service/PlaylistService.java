package dev.ardijorganxhi.listenify.service;

import dev.ardijorganxhi.listenify.entity.Playlist;
import dev.ardijorganxhi.listenify.entity.Song;
import dev.ardijorganxhi.listenify.entity.User;
import dev.ardijorganxhi.listenify.mapper.PlaylistMapper;
import dev.ardijorganxhi.listenify.mapper.SongMapper;
import dev.ardijorganxhi.listenify.model.PagingResult;
import dev.ardijorganxhi.listenify.model.dto.PlaylistDto;
import dev.ardijorganxhi.listenify.model.dto.SongDto;
import dev.ardijorganxhi.listenify.model.request.PaginationRequest;
import dev.ardijorganxhi.listenify.model.request.PlaylistRequest;
import dev.ardijorganxhi.listenify.repository.*;
import dev.ardijorganxhi.listenify.utils.PaginationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final UserRepository userRepository;
    private final PlaylistMapper playlistMapper;
    private final SongMapper songMapper;
    private final SongRepository songRepository;

    public PagingResult<PlaylistDto> getPlaylists(Long userId, PaginationRequest request) {
        final Pageable pageable = PaginationUtils.getPageable(request.getPage(), request.getSize(), request.getDirection(), request.getSortField());
        final Specification<Playlist> specification = PlaylistSpecification.getPlaylists(userId);
        final Page<Playlist> playlistPage = playlistRepository.findAll(specification, pageable);
        final List<PlaylistDto> playlists = playlistPage.stream().map(playlistMapper::toDto).toList();
        return new PagingResult<>(playlists,
                playlistPage.getTotalPages(),
                playlistPage.getTotalElements(),
                playlistPage.getSize(),
                playlistPage.getNumber(),
                playlistPage.isEmpty());

    }

    public void createPlaylist(Long userId, PlaylistRequest request) {
        User user = userRepository.findById(userId).orElseThrow();
        Playlist playlist = playlistMapper.createPlaylist(user, request);
        playlistRepository.save(playlist);
    }

    public void deletePlaylistById(Long playlistId, Long userId){
        Playlist playlist = playlistRepository.findByIdAndUserId(playlistId, userId).orElseThrow();
        playlist.setDeleted(true);
        playlistRepository.save(playlist);
    }

    public PagingResult<SongDto> getSongs(Long playlistId, Long userId, PaginationRequest request) {
        final Pageable pageable = PaginationUtils.getPageable(request.getPage(), request.getSize(), request.getDirection(), request.getSortField());
        final Specification<Song> specification = PlaylistSpecification.getSongs(playlistId, userId);
        final Page<Song> songPage = songRepository.findAll(specification, pageable);
        final List<SongDto> playlists = songPage.stream().map(songMapper::toDto).toList();
        return new PagingResult<>(playlists,
                songPage.getTotalPages(),
                songPage.getTotalElements(),
                songPage.getSize(),
                songPage.getNumber(),
                songPage.isEmpty());
    }
}

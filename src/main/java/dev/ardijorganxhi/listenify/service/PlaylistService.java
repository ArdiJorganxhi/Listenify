package dev.ardijorganxhi.listenify.service;

import dev.ardijorganxhi.listenify.entity.Playlist;
import dev.ardijorganxhi.listenify.entity.User;
import dev.ardijorganxhi.listenify.mapper.PlaylistMapper;
import dev.ardijorganxhi.listenify.model.PagingResult;
import dev.ardijorganxhi.listenify.model.dto.PlaylistDto;
import dev.ardijorganxhi.listenify.model.request.PaginationRequest;
import dev.ardijorganxhi.listenify.model.request.PlaylistRequest;
import dev.ardijorganxhi.listenify.repository.PlaylistRepository;
import dev.ardijorganxhi.listenify.repository.PlaylistSpecification;
import dev.ardijorganxhi.listenify.repository.UserRepository;
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

    public void deletePlaylistById(Long id){
        Playlist playlist = playlistRepository.findById(id).orElseThrow();
        playlist.setDeleted(true);
        playlistRepository.save(playlist);
    }
}

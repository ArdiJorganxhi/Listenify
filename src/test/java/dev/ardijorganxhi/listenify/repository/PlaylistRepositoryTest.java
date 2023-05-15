package dev.ardijorganxhi.listenify.repository;

import dev.ardijorganxhi.listenify.entity.Playlist;
import dev.ardijorganxhi.listenify.entity.Song;
import dev.ardijorganxhi.listenify.entity.SongPlaylist;
import dev.ardijorganxhi.listenify.entity.User;
import dev.ardijorganxhi.listenify.model.request.PaginationRequest;
import dev.ardijorganxhi.listenify.utils.PaginationUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class PlaylistRepositoryTest {

    @Mock
    private PlaylistRepository playlistRepository;

    @Mock
    private SongPlaylistRepository songPlaylistRepository;


    @Test
    public void it_should_find_by_playlistid_and_userid() {
        Long userId = 1L;
        Long playlistId = 1L;

        User user = User.builder()
                .id(userId)
                .listenifyname("user")
                .build();

        Playlist playlist = Playlist.builder()
                .id(playlistId)
                .name("playlist")
                .user(user)
                .build();

        when(playlistRepository.findByIdAndUserId(playlistId, userId)).thenReturn(Optional.of(playlist));
    }

    @Test
    public void it_should_find_all_with_specification_and_pageable() {
        User user = User.builder()
                .id(1L)
                .listenifyname("user")
                .build();
        Playlist playlist1 = Playlist.builder()
                .id(1L)
                .name("playlist1")
                .user(user)
                .build();
        Playlist playlist2 = Playlist.builder()
                .id(2L)
                .name("playlist2")
                .user(user)
                .build();

        List<Playlist> playlists = List.of(playlist1, playlist2);

        PaginationRequest request = new PaginationRequest();

        Specification<Playlist> specification = PlaylistSpecification.getPlaylists(user.getId());
        Pageable pageable = PaginationUtils.getPageable(request.getPage(), request.getSize(), request.getDirection(), request.getSortField());
        Page<Playlist> expectedPage = new PageImpl<>(playlists, pageable, playlists.size());

        when(playlistRepository.findAll(specification, pageable)).thenReturn(expectedPage);
    }

    @Test
    public void it_should_find_by_playlistid_and_songid() {
        Long playlistId = 1L;
        Long songId = 1L;

        Song song = Song.builder()
                .id(songId)
                .name("song")
                .build();

        Playlist playlist = Playlist.builder()
                .id(playlistId)
                .name("playlist")
                .build();

        SongPlaylist songPlaylist = SongPlaylist.builder()
                .playlist(playlist)
                .song(song)
                .build();

        when(songPlaylistRepository.findByPlaylistIdAndSongId(playlistId, songId)).thenReturn(songPlaylist);
    }
}

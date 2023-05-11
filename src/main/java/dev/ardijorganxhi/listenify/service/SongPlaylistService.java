package dev.ardijorganxhi.listenify.service;

import dev.ardijorganxhi.listenify.entity.Playlist;
import dev.ardijorganxhi.listenify.entity.SongPlaylist;
import dev.ardijorganxhi.listenify.entity.Song;
import dev.ardijorganxhi.listenify.entity.User;
import dev.ardijorganxhi.listenify.mapper.PlaylistMapper;
import dev.ardijorganxhi.listenify.repository.PlaylistRepository;
import dev.ardijorganxhi.listenify.repository.SongPlaylistRepository;
import dev.ardijorganxhi.listenify.repository.SongRepository;
import dev.ardijorganxhi.listenify.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SongPlaylistService {

    private final SongPlaylistRepository songPlaylistRepository;
    private final UserRepository userRepository;
    private final SongRepository songRepository;
    private final PlaylistMapper playlistMapper;
    private final PlaylistRepository playlistRepository;

    public void addSongToPlaylist(Long playlistId, Long songId, Long userId) {
        Playlist playlist = playlistRepository.findByIdAndUserId(playlistId, userId).orElseThrow();
        Song song = songRepository.findById(songId).orElseThrow();
        SongPlaylist songPlaylist = playlistMapper.addSongToPlaylist(playlist, song);
        songPlaylistRepository.save(songPlaylist);
    }

    public void deleteSongFromPlaylist(Long playlistId, Long songId){
        SongPlaylist songPlaylist = songPlaylistRepository.findByPlaylistIdAndSongId(playlistId, songId);
        songPlaylist.setDeleted(true);
        songPlaylistRepository.save(songPlaylist);
    }
}

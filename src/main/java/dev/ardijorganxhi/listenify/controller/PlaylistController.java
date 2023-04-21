package dev.ardijorganxhi.listenify.controller;

import dev.ardijorganxhi.listenify.model.request.PlaylistRequest;
import dev.ardijorganxhi.listenify.service.PlaylistService;
import dev.ardijorganxhi.listenify.service.SongPlaylistService;
import dev.ardijorganxhi.listenify.utils.MdcConstant;
import lombok.RequiredArgsConstructor;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/playlists")
@RequiredArgsConstructor
public class PlaylistController {

    private final PlaylistService playlistService;
    private final SongPlaylistService songPlaylistService;

    @PostMapping
    private void createPlaylist(@RequestBody PlaylistRequest request) {
        playlistService.createPlaylist(Long.valueOf(MDC.get(MdcConstant.X_USER_ID)), request);
    }

    @DeleteMapping("/{id}")
    private void deletePlaylistById(@PathVariable Long id){
        playlistService.deletePlaylistById(id);
    }

    @PostMapping("/{playlistId}/songs/{songId}")
    private void addSongToPlaylist(@PathVariable Long playlistId, @PathVariable Long songId) {
        songPlaylistService.addSongToPlaylist(playlistId, songId);
    }

    @DeleteMapping("/{playlistId}/songs/{songId}")
    private void deleteSongFromPlaylist(@PathVariable Long playlistId, @PathVariable Long songId) {
        songPlaylistService.deleteSongFromPlaylist(playlistId, songId);
    }
}

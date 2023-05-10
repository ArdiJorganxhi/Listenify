package dev.ardijorganxhi.listenify.controller;

import dev.ardijorganxhi.listenify.model.PagingResult;
import dev.ardijorganxhi.listenify.model.dto.SongDto;
import dev.ardijorganxhi.listenify.model.request.PaginationRequest;
import dev.ardijorganxhi.listenify.model.request.PlaylistRequest;
import dev.ardijorganxhi.listenify.service.PlaylistService;
import dev.ardijorganxhi.listenify.service.SongPlaylistService;
import dev.ardijorganxhi.listenify.utils.MdcConstant;
import lombok.RequiredArgsConstructor;
import org.slf4j.MDC;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/playlists")
@RequiredArgsConstructor
public class PlaylistController {

    private final PlaylistService playlistService;
    private final SongPlaylistService songPlaylistService;

    @PostMapping
    private void createPlaylist(@Valid @RequestBody PlaylistRequest request) {
        playlistService.createPlaylist(Long.valueOf(MDC.get(MdcConstant.X_USER_ID)), request);
    }

    @DeleteMapping("/{id}")
    private void deletePlaylistById(@PathVariable Long id){
        playlistService.deletePlaylistById(id);
    }

    @PostMapping("/{playlistId}/songs/{songId}")
    private void addSongToPlaylist(@RequestHeader(MdcConstant.X_USER_ID) Long userId, @PathVariable Long playlistId, @PathVariable Long songId) {
        System.out.println(userId);
        songPlaylistService.addSongToPlaylist(playlistId, songId);
    }

    @DeleteMapping("/{playlistId}/songs/{songId}")
    private void deleteSongFromPlaylist(@PathVariable Long playlistId, @PathVariable Long songId) {
        songPlaylistService.deleteSongFromPlaylist(playlistId, songId);
    }

    @GetMapping("/{playlistId}/songs")
    private ResponseEntity<PagingResult<SongDto>> getSongs(@PathVariable Long playlistId, PaginationRequest request) {
        return ResponseEntity.ok(playlistService.getSongs(playlistId, Long.valueOf(MDC.get(MdcConstant.X_USER_ID)), request));
    }
}

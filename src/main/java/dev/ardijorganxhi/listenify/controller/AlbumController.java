package dev.ardijorganxhi.listenify.controller;

import dev.ardijorganxhi.listenify.model.PagingResult;
import dev.ardijorganxhi.listenify.model.dto.AlbumDto;
import dev.ardijorganxhi.listenify.model.dto.SongDto;
import dev.ardijorganxhi.listenify.model.request.PaginationRequest;
import dev.ardijorganxhi.listenify.service.AlbumService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/albums")
@RequiredArgsConstructor
public class AlbumController {

    private final AlbumService albumService;

    @GetMapping("/{id}")
    public ResponseEntity<AlbumDto> getAlbumById(@PathVariable Long id){
        return ResponseEntity.ok(albumService.getAlbumById(id));
    }
    @DeleteMapping("/{id}")
    public void deleteAlbumById(@PathVariable Long id){
        albumService.deleteAlbumById(id);
    }

    @PostMapping("/{albumId}/songs/{songId}")
    public void addSongsToAlbum(@PathVariable Long albumId, @PathVariable Long songId) {
        albumService.addSongsToAlbum(albumId, songId);
    }
    @GetMapping("/{albumId}/songs")
    public ResponseEntity<PagingResult<SongDto>> getSongsOfAlbum(@PathVariable Long albumId, PaginationRequest request) {
        return ResponseEntity.ok(albumService.getSongsOfAlbum(albumId, request));
    }
}

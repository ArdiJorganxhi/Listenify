package dev.ardijorganxhi.listenify.controller;

import dev.ardijorganxhi.listenify.model.PagingResult;
import dev.ardijorganxhi.listenify.model.dto.SongDto;
import dev.ardijorganxhi.listenify.model.request.PaginationRequest;
import dev.ardijorganxhi.listenify.service.SongService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/songs")
@RequiredArgsConstructor
public class SongController {

    private final SongService songService;

    @GetMapping("/list")
    public ResponseEntity<PagingResult<SongDto>> findAllSongs(@RequestParam(required = false) String name, PaginationRequest request) {
        return ResponseEntity.ok(songService.findAllSongs(name, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SongDto> findSongById(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(songService.findSongById(id));
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        songService.deleteById(id);
    }
}

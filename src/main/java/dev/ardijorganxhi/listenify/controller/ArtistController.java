package dev.ardijorganxhi.listenify.controller;

import dev.ardijorganxhi.listenify.entity.Artist;
import dev.ardijorganxhi.listenify.model.PagingResult;
import dev.ardijorganxhi.listenify.model.dto.ArtistDto;
import dev.ardijorganxhi.listenify.model.dto.SongDto;
import dev.ardijorganxhi.listenify.model.dto.UserDto;
import dev.ardijorganxhi.listenify.model.request.ArtistRequest;
import dev.ardijorganxhi.listenify.model.request.PaginationRequest;
import dev.ardijorganxhi.listenify.model.request.SongRequest;
import dev.ardijorganxhi.listenify.service.ArtistService;
import dev.ardijorganxhi.listenify.service.SongService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/artists")
@RequiredArgsConstructor
public class ArtistController {

    private final ArtistService artistService;
    private final SongService songService;

    @GetMapping("/list")
    private ResponseEntity<PagingResult<ArtistDto>> findAllArtists(PaginationRequest request) {
        return ResponseEntity.ok(artistService.findAllArtists(request));
    }
    @PostMapping
    private void registerArtist(@RequestBody ArtistRequest request) {
        artistService.registerArtist(request);
    }

    @GetMapping("/{id}")
    private ResponseEntity<ArtistDto> getArtist(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(artistService.findById(id));
    }

    @DeleteMapping("/{id}")
    private void deleteArtistById(@PathVariable Long id){
        artistService.deleteById(id);
    }

    @PostMapping("/{id}/songs")
    private void registerSong(@RequestBody SongRequest request, @PathVariable Long id) {
        songService.registerSong(request, id);
    }

    @GetMapping("/{id}/songs")
    private ResponseEntity<PagingResult<SongDto>> getSongsByArtistId(@PathVariable Long id, PaginationRequest request) {
        return ResponseEntity.ok(artistService.findSongsByArtistId(id, request));
    }

}

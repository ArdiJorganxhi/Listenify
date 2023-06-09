package dev.ardijorganxhi.listenify.controller;

import dev.ardijorganxhi.listenify.entity.Artist;
import dev.ardijorganxhi.listenify.model.PagingResult;
import dev.ardijorganxhi.listenify.model.dto.AlbumDto;
import dev.ardijorganxhi.listenify.model.dto.ArtistDto;
import dev.ardijorganxhi.listenify.model.dto.SongDto;
import dev.ardijorganxhi.listenify.model.dto.UserDto;
import dev.ardijorganxhi.listenify.model.request.AlbumRequest;
import dev.ardijorganxhi.listenify.model.request.ArtistRequest;
import dev.ardijorganxhi.listenify.model.request.PaginationRequest;
import dev.ardijorganxhi.listenify.model.request.SongRequest;
import dev.ardijorganxhi.listenify.repository.AlbumSpecification;
import dev.ardijorganxhi.listenify.service.AlbumService;
import dev.ardijorganxhi.listenify.service.ArtistService;
import dev.ardijorganxhi.listenify.service.SongService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/artists")
@RequiredArgsConstructor
public class ArtistController {

    private final ArtistService artistService;
    private final SongService songService;
    private final AlbumService albumService;

    @GetMapping("/list")
    public ResponseEntity<PagingResult<ArtistDto>> findAllArtists(@RequestParam(required = false) String name, PaginationRequest request) {
        return ResponseEntity.ok(artistService.findAllArtists(name, request));
    }
    @PostMapping
    public void registerArtist(@Valid @RequestBody ArtistRequest request) {
        artistService.registerArtist(request);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArtistDto> getArtist(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(artistService.findById(id));
    }

    @DeleteMapping("/{id}")
    public void deleteArtistById(@PathVariable Long id){
        artistService.deleteById(id);
    }

    @PostMapping("/{id}/songs")
    public void registerSong(@Valid @RequestBody SongRequest request, @PathVariable Long id) {
        songService.registerSong(request, id);
    }

    @GetMapping("/{id}/songs")
    public ResponseEntity<PagingResult<SongDto>> getSongsByArtistId(@PathVariable Long id, PaginationRequest request) {
        return ResponseEntity.ok(artistService.findSongsByArtistId(id, request));
    }
    @PostMapping("/{id}/albums")
    public void createAlbum(@Valid @RequestBody AlbumRequest request, @PathVariable Long id) {
        albumService.createAlbum(request, id);
    }

    @GetMapping("/{id}/albums")
    public ResponseEntity<PagingResult<AlbumDto>> getAlbumsByArtist(@PathVariable Long id, PaginationRequest request) {
        return ResponseEntity.ok(albumService.getAlbumsByArtist(id, request));
    }

}

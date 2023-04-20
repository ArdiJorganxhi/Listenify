package dev.ardijorganxhi.listenify.controller;

import dev.ardijorganxhi.listenify.entity.Artist;
import dev.ardijorganxhi.listenify.model.PagingResult;
import dev.ardijorganxhi.listenify.model.dto.ArtistDto;
import dev.ardijorganxhi.listenify.model.dto.UserDto;
import dev.ardijorganxhi.listenify.model.request.ArtistRequest;
import dev.ardijorganxhi.listenify.model.request.PaginationRequest;
import dev.ardijorganxhi.listenify.service.ArtistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/artists")
@RequiredArgsConstructor
public class ArtistController {

    private final ArtistService artistService;

    @GetMapping("/list")
    private ResponseEntity<PagingResult<ArtistDto>> findAllArtists(PaginationRequest request) {
        return ResponseEntity.ok(artistService.findAllArtists(request));
    }
    @PostMapping
    private void registerArtist(@RequestBody ArtistRequest request) {
        artistService.registerArtist(request);
    }

    @GetMapping("/{id}")
    private ResponseEntity<ArtistDto> getArtist(@PathVariable Long id) {
        return ResponseEntity.ok(artistService.findById(id));
    }

    @DeleteMapping("/{id}")
    private void deleteArtistById(@PathVariable Long id){
        artistService.deleteById(id);
    }

}

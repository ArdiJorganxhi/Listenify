package dev.ardijorganxhi.listenify.service;

import dev.ardijorganxhi.listenify.entity.Artist;
import dev.ardijorganxhi.listenify.entity.User;
import dev.ardijorganxhi.listenify.mapper.ArtistMapper;
import dev.ardijorganxhi.listenify.model.PagingResult;
import dev.ardijorganxhi.listenify.model.dto.ArtistDto;
import dev.ardijorganxhi.listenify.model.dto.UserDto;
import dev.ardijorganxhi.listenify.model.request.ArtistRequest;
import dev.ardijorganxhi.listenify.model.request.PaginationRequest;
import dev.ardijorganxhi.listenify.repository.ArtistRepository;
import dev.ardijorganxhi.listenify.repository.ArtistSpecification;
import dev.ardijorganxhi.listenify.repository.UserSpecification;
import dev.ardijorganxhi.listenify.utils.PaginationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArtistService {

    private final ArtistRepository artistRepository;
    private final ArtistMapper artistMapper;

    public PagingResult<ArtistDto> findAllArtists(PaginationRequest request) {
        final Pageable pageable = PaginationUtils.getPageable(request.getPage(), request.getSize(), request.getDirection(), request.getSortField());
        final Specification<Artist> specification = ArtistSpecification.getArtists();
        final Page<Artist> artistPage = artistRepository.findAll(specification, pageable);
        final List<ArtistDto> artists = artistPage.stream().map(artistMapper::toDto).toList();
        return new PagingResult<>(artists,
                artistPage.getTotalPages(),
                artistPage.getTotalElements(),
                artistPage.getSize(),
                artistPage.getNumber(),
                artistPage.isEmpty());

    }

    public void registerArtist(ArtistRequest request) {
        Artist artist = artistMapper.registerArtist(request);
        artistRepository.save(artist);
    }

    public ArtistDto findById(Long id) throws Exception {
        Artist artist = artistRepository.findById(id).orElseThrow();
        if(!artist.isDeleted()){
            return artistMapper.toDto(artist);
        } else {
            throw new Exception("Artist not found!");
        }

    }

    public void deleteById(Long id) {
        Artist artist = artistRepository.findById(id).orElseThrow();
        artist.setDeleted(true);
        artistRepository.save(artist);
    }
}

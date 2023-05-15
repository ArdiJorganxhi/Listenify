package dev.ardijorganxhi.listenify.repository;

import dev.ardijorganxhi.listenify.entity.Artist;
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

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ArtistRepositoryTest {

    @Mock
    private ArtistRepository artistRepository;

    @Test
    public void it_should_find_all_with_specification_and_pageable() {
        Artist artist1 = Artist.builder()
                .id(1L)
                .listenifyname("artist1")
                .build();
        Artist artist2 = Artist.builder()
                .id(2L)
                .listenifyname("artist2")
                .build();

        List<Artist> artists = List.of(artist1, artist2);

        PaginationRequest request = new PaginationRequest();

        Specification<Artist> specification = ArtistSpecification.getArtists("");
        Pageable pageable = PaginationUtils.getPageable(request.getPage(), request.getSize(), request.getDirection(), request.getSortField());
        Page<Artist> expectedPage = new PageImpl<>(artists, pageable, artists.size());

        when(artistRepository.findAll(specification, pageable)).thenReturn(expectedPage);
    }
}

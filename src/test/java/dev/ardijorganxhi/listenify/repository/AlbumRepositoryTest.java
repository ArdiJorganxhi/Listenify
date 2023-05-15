package dev.ardijorganxhi.listenify.repository;

import dev.ardijorganxhi.listenify.entity.Album;
import dev.ardijorganxhi.listenify.entity.Artist;
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
public class AlbumRepositoryTest {

    @Mock
    private AlbumRepository albumRepository;

    @Test
    public void it_should_find_all_with_specification_and_pageable() {
        Artist artist = Artist.builder()
                .id(1L)
                .listenifyname("artist")
                .build();

        Album album1 = Album.builder()
                .id(1L)
                .name("album1")
                .build();
        Album album2 = Album.builder()
                .id(2L)
                .name("artist2")
                .build();

        List<Album> albums = List.of(album1, album2);

        PaginationRequest request = new PaginationRequest();

        Specification<Album> specification = AlbumSpecification.getAlbumsByArtist(artist.getId());
        Pageable pageable = PaginationUtils.getPageable(request.getPage(), request.getSize(), request.getDirection(), request.getSortField());
        Page<Album> expectedPage = new PageImpl<>(albums, pageable, albums.size());

        when(albumRepository.findAll(specification, pageable)).thenReturn(expectedPage);
    }
}

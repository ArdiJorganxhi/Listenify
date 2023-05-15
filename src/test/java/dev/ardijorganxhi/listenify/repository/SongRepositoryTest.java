package dev.ardijorganxhi.listenify.repository;

import dev.ardijorganxhi.listenify.entity.Artist;
import dev.ardijorganxhi.listenify.entity.Song;
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
public class SongRepositoryTest {

    @Mock
    private SongRepository songRepository;

    @Test
    public void it_should_find_all_with_specification_and_pageable() {
        Song song1 = Song.builder()
                .id(1L)
                .name("song1")
                .build();
        Song song2 = Song.builder()
                .id(2L)
                .name("song2")
                .build();

        List<Song> songs = List.of(song1, song2);

        PaginationRequest request = new PaginationRequest();

        Specification<Song> specification = SongSpecification.getSongs("");
        Pageable pageable = PaginationUtils.getPageable(request.getPage(), request.getSize(), request.getDirection(), request.getSortField());
        Page<Song> expectedPage = new PageImpl<>(songs, pageable, songs.size());

        when(songRepository.findAll(specification, pageable)).thenReturn(expectedPage);
    }
}

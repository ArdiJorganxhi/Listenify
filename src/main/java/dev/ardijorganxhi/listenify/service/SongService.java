package dev.ardijorganxhi.listenify.service;

import dev.ardijorganxhi.listenify.repository.SongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SongService {

    private final SongRepository songRepository;

}

package dev.ardijorganxhi.listenify.repository;

import dev.ardijorganxhi.listenify.entity.Song;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SongRepository extends JpaRepository<Song, Long> {
}

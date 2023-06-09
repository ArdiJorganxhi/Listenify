package dev.ardijorganxhi.listenify.repository;

import dev.ardijorganxhi.listenify.entity.Playlist;
import dev.ardijorganxhi.listenify.entity.Song;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Long> {

    Page<Playlist> findAll(Specification<Playlist> spec, Pageable pageable);
    Optional<Playlist> findByIdAndUserId(Long playlistId, Long userId);


}

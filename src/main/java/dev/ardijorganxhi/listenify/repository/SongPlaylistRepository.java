package dev.ardijorganxhi.listenify.repository;

import dev.ardijorganxhi.listenify.entity.SongPlaylist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SongPlaylistRepository extends JpaRepository<SongPlaylist, Long> {

    SongPlaylist findByPlaylistIdAndSongId(Long playlistId, Long songId);
}

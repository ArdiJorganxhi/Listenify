package dev.ardijorganxhi.listenify.repository;

import dev.ardijorganxhi.listenify.entity.Playlist;
import dev.ardijorganxhi.listenify.entity.Song;
import dev.ardijorganxhi.listenify.entity.SongPlaylist;
import dev.ardijorganxhi.listenify.entity.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PlaylistSpecification {

    public static Specification<Playlist> getPlaylists(Long userId) {
        return (root, query, cb) -> {
            Join<Playlist, User> userJoin = root.join("user");
            Predicate userPredicate = cb.equal(userJoin.get("id"), userId);
            Predicate deletedPredicate = cb.isFalse(root.get("deleted"));
            return cb.and(userPredicate, deletedPredicate);
        };
    }

    public static Specification<Song> getSongs(Long playlistId, Long userId) {
        return (root, query, cb) -> {
          Join<Song, SongPlaylist> songPlaylistJoin = root.join("playlists");
          Join<SongPlaylist, Playlist> playlistJoin = songPlaylistJoin.join("playlist");
          Join<Playlist, User> userJoin = playlistJoin.join("user");
          Predicate userPredicate = cb.equal(userJoin.get("id"), userId);
          Predicate songPredicate = cb.equal(playlistJoin.get("id"), playlistId);
          Predicate deletedPredicate = cb.isFalse(root.get("deleted"));
          return cb.and(userPredicate, songPredicate, deletedPredicate);
        };
    }
}

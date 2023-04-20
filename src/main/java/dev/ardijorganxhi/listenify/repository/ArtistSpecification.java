package dev.ardijorganxhi.listenify.repository;

import dev.ardijorganxhi.listenify.entity.Artist;
import dev.ardijorganxhi.listenify.entity.Song;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ArtistSpecification {

    public static Specification<Artist> getArtists() {
        return (root, query, cb) -> cb.isFalse(root.get("deleted"));
    }

    public static Specification<Song> getSongsByArtistId(Long artistId) {
        return (root, query, cb) -> {
            Join<Song, Artist> artistJoin = root.join("artist");
            return cb.equal(artistJoin.get("id"), artistId);
        };
    }
}

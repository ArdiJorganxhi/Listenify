package dev.ardijorganxhi.listenify.repository;

import dev.ardijorganxhi.listenify.entity.Album;
import dev.ardijorganxhi.listenify.entity.Artist;
import dev.ardijorganxhi.listenify.entity.Song;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AlbumSpecification {

    public static Specification<Album> getAlbumsByArtist(Long artistId) {
        return (root, query, cb) -> {
            Join<Album, Artist> artistJoin = root.join("artist");
            Predicate artistPredicate = cb.equal(artistJoin.get("id"), artistId);
            Predicate deletedPredicate = cb.isFalse(root.get("deleted"));
            return cb.and(artistPredicate, deletedPredicate);
        };
    }

    public static Specification<Song> getSongsOfAlbum(Long albumId) {
        return (root, query, cb) -> {
            Join<Song, Album> albumJoin = root.join("album");
            return cb.equal(albumJoin.get("id"), albumId);
        };
    }
}

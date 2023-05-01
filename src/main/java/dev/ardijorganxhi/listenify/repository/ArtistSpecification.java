package dev.ardijorganxhi.listenify.repository;

import dev.ardijorganxhi.listenify.entity.Artist;
import dev.ardijorganxhi.listenify.entity.Song;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import javax.persistence.criteria.Join;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ArtistSpecification {

    public static Specification<Artist> getArtists(String name) {

        return (root, query, cb) -> {
            if(StringUtils.isBlank(name)) {
               return cb.isFalse(root.get("deleted"));
            } else {
                return cb.like(cb.lower(root.get("name")), likePattern(name));
            }
        };
    }

    public static Specification<Song> getSongsByArtistId(Long artistId) {
        return (root, query, cb) -> {
            Join<Song, Artist> artistJoin = root.join("artist");
            return cb.equal(artistJoin.get("id"), artistId);
        };
    }

    private static String likePattern(String value) {
        return "%" + value.toLowerCase() + "%";
    }
}

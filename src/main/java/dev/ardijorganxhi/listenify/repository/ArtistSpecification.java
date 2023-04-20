package dev.ardijorganxhi.listenify.repository;

import dev.ardijorganxhi.listenify.entity.Artist;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ArtistSpecification {

    public static Specification<Artist> getArtists() {
        return (root, query, cb) -> cb.isFalse(root.get("deleted"));
    }
}

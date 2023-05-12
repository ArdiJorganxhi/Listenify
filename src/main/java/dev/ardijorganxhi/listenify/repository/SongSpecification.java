package dev.ardijorganxhi.listenify.repository;

import dev.ardijorganxhi.listenify.entity.Artist;
import dev.ardijorganxhi.listenify.entity.Song;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SongSpecification {

    public static Specification<Song> getSongs(String name) {

        return (root, query, cb) -> {
            if(StringUtils.isBlank(name)) {
                return cb.isFalse(root.get("deleted"));
            } else {
                return cb.like(cb.lower(root.get("name")), likePattern(name));
            }
        };
    }

    private static String likePattern(String value) {
        return "%" + value.toLowerCase() + "%";
    }
}

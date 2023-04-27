package dev.ardijorganxhi.listenify.repository;

import dev.ardijorganxhi.listenify.entity.User;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@NoArgsConstructor
public class UserSpecification {
    public static Specification<User> getActiveUsers() {

        return (root, equal, cb) -> cb.isFalse(root.get("deleted"));
    }
}

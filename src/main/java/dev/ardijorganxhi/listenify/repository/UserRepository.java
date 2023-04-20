package dev.ardijorganxhi.listenify.repository;

import dev.ardijorganxhi.listenify.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}

package repository;

import model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Long, UserEntity> {
    Optional<UserEntity> findByUsername(String username);
}

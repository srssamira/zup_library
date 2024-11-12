package com.library.zup_library.repositories;

import com.library.zup_library.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByNickname(String nickname);

    boolean existsByNickname(String nickname);
}

package com.sparta.intellipicktask.repository;

import com.sparta.intellipicktask.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Long, User> {
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);
}

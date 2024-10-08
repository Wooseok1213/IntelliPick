package com.sparta.intellipicktask.repository;

import com.sparta.intellipicktask.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByPassword(String password);
    Optional<User> findByNickname(String nickname);
    Optional<User> findByEmail(String email);


    Object saveUser(Object any);
    Object existsByUsername(String username);
    Object existsByEmail(String email);
}

package com.javariga6.yugioh.repository;

import com.javariga6.yugioh.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findFirstByEmail(String email);
    Boolean existsByEmail(String email);

}

package com.javariga6.yugioh.repository;

import com.javariga6.yugioh.model.PassRecoveryToken;
import com.javariga6.yugioh.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PassRecoveryTokenRepository extends JpaRepository<PassRecoveryToken, Long> {
    Optional<PassRecoveryToken> findFirstByUser(User user);
    Optional<PassRecoveryToken> findFirstByToken(String token);
}

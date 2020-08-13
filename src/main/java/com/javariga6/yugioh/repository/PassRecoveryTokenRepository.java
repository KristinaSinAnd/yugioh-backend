package com.javariga6.yugioh.repository;

import com.javariga6.yugioh.model.PassRecoveryToken;
import com.javariga6.yugioh.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PassRecoveryTokenRepository extends JpaRepository<PassRecoveryToken, Long> {
    PassRecoveryToken findFirstByUser(User user);
    PassRecoveryToken findFirstByToken(String token);
}

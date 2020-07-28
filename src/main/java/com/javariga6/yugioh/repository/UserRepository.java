package com.javariga6.yugioh.repository;

import com.javariga6.yugioh.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User getFirstByEmail(String email);

}

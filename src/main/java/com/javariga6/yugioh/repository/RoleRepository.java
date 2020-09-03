package com.javariga6.yugioh.repository;

import com.javariga6.yugioh.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role getFirstByRole(String role);

}

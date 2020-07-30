package com.javariga6.yugioh.repository;

import com.javariga6.yugioh.model.CardStorage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardStorageRepository extends JpaRepository<CardStorage, Long> {
}

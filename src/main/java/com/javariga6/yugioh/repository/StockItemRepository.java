package com.javariga6.yugioh.repository;

import com.javariga6.yugioh.model.StockItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockItemRepository extends JpaRepository<StockItem, Long > {
    boolean existsById(Long id);
}

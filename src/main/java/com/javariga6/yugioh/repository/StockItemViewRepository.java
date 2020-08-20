package com.javariga6.yugioh.repository;

import com.javariga6.yugioh.model.StockItemView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockItemViewRepository extends JpaRepository<StockItemView, Long> {
}

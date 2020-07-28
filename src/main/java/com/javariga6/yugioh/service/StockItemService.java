package com.javariga6.yugioh.service;

import com.javariga6.yugioh.model.StockItem;
import com.javariga6.yugioh.repository.StockItemRepository;
import org.springframework.stereotype.Service;

@Service
public class StockItemService {
    final StockItemRepository stockItemRepository;

    public StockItemService(StockItemRepository stockItemRepository) { this.stockItemRepository = stockItemRepository; }

    public void saveStockItem(StockItem stockItem) {stockItemRepository.save(stockItem);}


    public void delete(StockItem stockItem) {
        stockItemRepository.delete(stockItem);
    }

    public void deleteById(Long id) {
        stockItemRepository.deleteById(id);
    }
}

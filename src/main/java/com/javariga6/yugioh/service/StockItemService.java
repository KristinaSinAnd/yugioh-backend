package com.javariga6.yugioh.service;

import com.javariga6.yugioh.model.Article;
import com.javariga6.yugioh.model.StockItem;
import com.javariga6.yugioh.repository.StockItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockItemService {
    final StockItemRepository stockItemRepository;

    public StockItemService(StockItemRepository stockItemRepository) { this.stockItemRepository = stockItemRepository; }

    public void saveStockItem(StockItem stockItem) { stockItemRepository.save(stockItem);}

    public void delete(StockItem stockItem) { stockItemRepository.delete(stockItem); }

    public void deleteStockItemById(Long id) { stockItemRepository.deleteById(id); }

    public void findStockItemById(Long id) { stockItemRepository.findById(id); }

    public List<StockItem> findAllStockItems(){
        return stockItemRepository.findAll();
    }

    }
}

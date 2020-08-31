package com.javariga6.yugioh.service;

import com.javariga6.yugioh.exceptions.ResourceNotFoundException;
import com.javariga6.yugioh.model.Article;
import com.javariga6.yugioh.model.CardStorage;
import com.javariga6.yugioh.model.StockItem;
import com.javariga6.yugioh.repository.StockItemRepository;
import com.sun.istack.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockItemService {
    final StockItemRepository stockItemRepository;


    public StockItemService(StockItemRepository stockItemRepository) { this.stockItemRepository = stockItemRepository; }

    public StockItem saveStockItem(StockItem stockItem) {
        return stockItemRepository.save(stockItem);
    }

    public void delete(StockItem stockItem) { stockItemRepository.delete(stockItem); }

    public void deleteStockItemById(Long id) { stockItemRepository.deleteById(id); }

    public void findStockItemById(Long id) { stockItemRepository.findById(id); }


    public List<StockItem> findAllStockItems(){
        return stockItemRepository.findAll();
    }


    public StockItem updateStockItem(@NotNull StockItem stockItem) {
        StockItem stockItemOriginal = stockItemRepository
                .findById(stockItem.getId())
                .orElseThrow(ResourceNotFoundException::new);
        stockItem.setArticle(stockItemOriginal.getArticle());
        return stockItemRepository.save(stockItem);
    }


}


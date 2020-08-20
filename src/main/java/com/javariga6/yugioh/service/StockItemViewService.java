package com.javariga6.yugioh.service;

import com.javariga6.yugioh.model.StockItemView;
import com.javariga6.yugioh.repository.StockItemViewRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockItemViewService {
    private final StockItemViewRepository stockItemViewRepository;

    public StockItemViewService(StockItemViewRepository stockItemViewRepository) {
        this.stockItemViewRepository = stockItemViewRepository;
    }

    public List<StockItemView> getAll(){
        return stockItemViewRepository.findAll();
    }
}

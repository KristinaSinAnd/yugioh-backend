package com.javariga6.yugioh.controller;

import com.javariga6.yugioh.model.StockItemView;
import com.javariga6.yugioh.service.StockItemViewService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("stockitemview")
public class StockItemViewController {
    private final StockItemViewService stockItemViewService;

    public StockItemViewController(StockItemViewService stockItemViewService) {
        this.stockItemViewService = stockItemViewService;
    }

    @GetMapping("all")
    public List<StockItemView> getAll() {
        return stockItemViewService.getAll();
    }
}

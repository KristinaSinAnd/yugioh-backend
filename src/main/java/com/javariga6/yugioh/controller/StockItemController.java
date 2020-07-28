package com.javariga6.yugioh.controller;

import com.javariga6.yugioh.model.StockItem;
import com.javariga6.yugioh.service.StockItemService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stockitem")
public class StockItemController {

    private final StockItemService stockItemService;

    public StockItemController(StockItemService stockItemService) { this.stockItemService = stockItemService; }

    @PostMapping
    public void save(@RequestBody StockItem stockitem ){stockItemService.saveStockItem(stockitem);}
}

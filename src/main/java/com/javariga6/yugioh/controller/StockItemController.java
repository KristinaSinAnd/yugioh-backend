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
    public void save(@RequestBody StockItem stockItem ){stockItemService.saveStockItem(stockItem);}

    @PostMapping("/delete")
    public void delete(@RequestBody StockItem stockItem) {stockItemService.delete(stockItem);}



}

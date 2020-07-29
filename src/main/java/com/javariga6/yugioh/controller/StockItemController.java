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

    @PutMapping
    public void update(@RequestBody StockItem stockItem){stockItemService.updateStockItem(stockItem);}

    @PostMapping("/delete")
    public void delete(@RequestBody StockItem stockItem) {stockItemService.delete(stockItem);}

    @GetMapping ("/delete/id/{id}")
    public void deleteStockItemById(@PathVariable Long id){ stockItemService.deleteStockItemById(id);}

    @GetMapping("/get/id/{id}")
    public void findStockItemById(@PathVariable Long id){ stockItemService.findStockItemById(id);}

    @GetMapping("get/all")
    public void findAllStockItems(@RequestBody StockItem stockItem){stockItemService.findAllStockItems();}


    }

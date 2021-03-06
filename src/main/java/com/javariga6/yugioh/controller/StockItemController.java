package com.javariga6.yugioh.controller;

import com.javariga6.yugioh.model.StockItem;
import com.javariga6.yugioh.service.StockItemService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/stockitem")
public class StockItemController {

    private final StockItemService stockItemService;

    public StockItemController(StockItemService stockItemService) {
        this.stockItemService = stockItemService;
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    public void save(@RequestBody StockItem stockItem) {
        stockItemService.saveStockItem(stockItem);
    }

    @PostMapping ("/update")
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    public void update(@RequestBody StockItem stockItem) {
        stockItemService.updateStockItem(stockItem);
    }


    @PostMapping("/delete")
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    public void delete(@RequestBody StockItem stockItem) {
        stockItemService.delete(stockItem);
    }

    @GetMapping("/delete/id/{id}")
    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    public void deleteStockItemById(@PathVariable Long id) {
        stockItemService.deleteStockItemById(id);
    }

    @GetMapping("/get/id/{id}")
    public void findStockItemById(@PathVariable Long id) {
        stockItemService.findStockItemById(id);
    }

    @GetMapping("get/all")
    public List<StockItem> findAllStockItems() {
        return stockItemService.findAllStockItems();
    }

}

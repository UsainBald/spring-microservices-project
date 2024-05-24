package com.practice.inventory.controllers;

import com.practice.inventory.repositories.InventoryRepository;
import com.practice.inventory.services.InventoryService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryRepository inventoryRepository;
    private final InventoryService inventoryService;

    @GetMapping("/{sku-code}")
    @ResponseStatus
    public boolean isInStock(@PathVariable("sku-code") String skuCode) {
        return inventoryService.isInStock(skuCode);
    }
}

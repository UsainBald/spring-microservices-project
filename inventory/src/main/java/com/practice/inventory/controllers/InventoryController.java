package com.practice.inventory.controllers;

import com.practice.inventory.dataTransferObjects.InventoryRequest;
import com.practice.inventory.dataTransferObjects.InventoryResponse;
import com.practice.inventory.repositories.InventoryRepository;
import com.practice.inventory.services.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

  private final InventoryRepository inventoryRepository;
  private final InventoryService inventoryService;

  @PostMapping
  public ResponseEntity<?> isInStock(@RequestBody List<InventoryRequest> items) {

    String result = inventoryService.isInStock(items);
    if (result.equals("ERROR")) {
      return new ResponseEntity<>("Failed to satisfy request", HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>(result, HttpStatus.OK);
  }
}

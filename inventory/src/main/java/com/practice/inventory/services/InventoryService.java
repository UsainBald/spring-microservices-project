package com.practice.inventory.services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.practice.inventory.dataTransferObjects.InventoryRequest;
import com.practice.inventory.modules.Inventory;
import com.practice.inventory.dataTransferObjects.InventoryResponse;
import com.practice.inventory.repositories.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InventoryService {
  private final InventoryRepository inventoryRepository;

  private final Gson gson;

  @Transactional(readOnly = true)
  public String isInStock(List<InventoryRequest> itemList) {

    List<InventoryResponse> responseList = new ArrayList<>();
    if (itemList == null || itemList.isEmpty()) {
      return "ERROR";
    }
    for (InventoryRequest item : itemList) {
      if (item.getSkuCode() == null || item.getSkuCode().isEmpty()) {
        return "ERROR";
      }
      Optional<Inventory> itemToCheck = inventoryRepository.findBySkuCode(item.getSkuCode());
      boolean available = itemToCheck.isPresent() && itemToCheck.get().getQuantity() >= item.getQuantity();
      responseList.add(InventoryResponse.builder()
          .skuCode(item.getSkuCode())
          .available(available)
          .build());
    }

    return gson.toJson(responseList);
  }
}

package com.practice.inventory.dataTransferObjects;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InventoryResponse {
  private String skuCode;
  private Boolean available;
}

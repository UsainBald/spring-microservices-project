package com.practice.order.dataTransferObjects;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InventoryResponse {
  private String skuCode;
  private Boolean available;
}

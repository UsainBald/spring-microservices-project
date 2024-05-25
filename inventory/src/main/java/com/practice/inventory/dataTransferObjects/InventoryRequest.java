package com.practice.inventory.dataTransferObjects;

import lombok.Data;

@Data
public class InventoryRequest {

  private String skuCode;
  private Integer quantity;

}
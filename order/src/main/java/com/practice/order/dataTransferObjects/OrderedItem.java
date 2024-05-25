package com.practice.order.dataTransferObjects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderedItem {
  private Long id;
  private String skuCode;
  private BigDecimal price;
  private Integer quantity;
}

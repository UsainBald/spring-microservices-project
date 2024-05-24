package com.practice.product.dataTransferObjects;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse {
  private String id;
  private String name;
  private String description;
  private BigDecimal price;
}


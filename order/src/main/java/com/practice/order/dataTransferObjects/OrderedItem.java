package com.practice.order.dataTransferObjects;

import jakarta.persistence.*;
import lombok.*;

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

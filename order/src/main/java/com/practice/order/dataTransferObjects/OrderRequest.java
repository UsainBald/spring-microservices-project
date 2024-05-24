package com.practice.order.dataTransferObjects;

import com.practice.order.models.Item;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
    private List<OrderedItem> orderedItems;
}

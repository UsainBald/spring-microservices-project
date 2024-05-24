package com.practice.order.services;

import com.practice.order.dataTransferObjects.OrderRequest;
import com.practice.order.dataTransferObjects.OrderedItem;
import com.practice.order.models.Item;
import com.practice.order.models.Order;
import com.practice.order.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor()
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;

    public void createOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<Item> items = orderRequest.getOrderedItems()
                .stream()
                .map(this::mapOrderRequestToOrder)
                .toList();

        order.setItems(items);

        orderRepository.save(order);
    }

    private Item mapOrderRequestToOrder(OrderedItem orderedItem) {
        Item item = new Item();
        item.setQuantity(orderedItem.getQuantity());
        item.setPrice(orderedItem.getPrice());
        item.setSkuCode(orderedItem.getSkuCode());
        return item;
    }
}

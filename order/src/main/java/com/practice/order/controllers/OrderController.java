package com.practice.order.controllers;

import com.practice.order.dataTransferObjects.OrderRequest;
import com.practice.order.models.Order;
import com.practice.order.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createOrder(@RequestBody OrderRequest order) {
        orderService.createOrder(order);
        return "Order created successfully!";
    }
}

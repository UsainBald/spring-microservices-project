package com.practice.order.controllers;

import com.practice.order.dataTransferObjects.OrderRequest;
import com.practice.order.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

  private final OrderService orderService;

  @PostMapping
  public CompletableFuture<ResponseEntity<String>> createOrder(@RequestBody OrderRequest order) {
    return orderService.createOrder(order)
        .thenApply(orderCreated -> {
          if (orderCreated) {
            return new ResponseEntity<>("Order created successfully!", HttpStatus.CREATED);
          } else {
            return new ResponseEntity<>("Order creation failed!", HttpStatus.INTERNAL_SERVER_ERROR);
          }


        });
  }
}

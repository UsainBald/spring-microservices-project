package com.practice.order.controllers;

import com.practice.order.dataTransferObjects.OrderRequest;
import com.practice.order.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

  private final OrderService orderService;

  @PostMapping
  public ResponseEntity<?> createOrder(@RequestBody OrderRequest order) {
    if (orderService.createOrder(order)) {
      return new ResponseEntity<>("Order created successfully!", HttpStatus.CREATED);
    }
    return new ResponseEntity<>("Order creation failed!", HttpStatus.INTERNAL_SERVER_ERROR);
  }
}

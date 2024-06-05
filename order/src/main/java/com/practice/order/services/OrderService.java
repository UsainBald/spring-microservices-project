package com.practice.order.services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.practice.order.dataTransferObjects.InventoryRequest;
import com.practice.order.dataTransferObjects.InventoryResponse;
import com.practice.order.dataTransferObjects.OrderRequest;
import com.practice.order.dataTransferObjects.OrderedItem;
import com.practice.order.models.Item;
import com.practice.order.models.Order;
import com.practice.order.repositories.OrderRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderService {

  private final OrderRepository orderRepository;
  private final WebClient.Builder webClient;
  private final Gson gson;

  public CompletableFuture<Boolean> fallbackMethod(OrderRequest orderRequest, RuntimeException runtimeException) {
    log.error(runtimeException.getMessage(), runtimeException);
    return CompletableFuture.supplyAsync(() -> false);
  }

  @CircuitBreaker(name = "inventory", fallbackMethod = "fallbackMethod")
  @TimeLimiter(name = "inventory")
  @Retry(name = "inventory")
  public CompletableFuture<Boolean> createOrder(OrderRequest orderRequest) {

    if (orderRequest == null) {
      return CompletableFuture.supplyAsync(() -> false);
    }

    Order order = new Order();
    order.setOrderNumber(UUID.randomUUID().toString());

    List<Item> items = orderRequest.getOrderedItems()
        .stream()
        .map(this::mapOrderRequestToOrder)
        .toList();

    order.setItems(items);

    List<InventoryRequest> inventoryRequests = orderRequest.getOrderedItems()
        .stream()
        .map(this::mapOrderRequestToInventoryRequest)
        .toList();

    String inventoryRequestsJson = gson.toJson(inventoryRequests);

    CompletableFuture<String> resultFuture = webClient.build().post()
        .uri("http://inventory/api/inventory")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(inventoryRequestsJson)
        .retrieve()
        .bodyToMono(String.class)
        .toFuture();

    return resultFuture.thenApply(result -> {
      System.out.println(result);
      assert result != null;
      if (result.equals("ERROR")) {
        return false;
      }

      List<InventoryResponse> itemList = gson.fromJson(result, new TypeToken<List<InventoryResponse>>() {}.getType());

      boolean allInStock = itemList.stream()
          .allMatch(InventoryResponse::getAvailable);

      if (allInStock) {
        orderRepository.save(order);
      }

      return allInStock;
    });
  }

  private InventoryRequest mapOrderRequestToInventoryRequest(OrderedItem orderedItem) {
    return InventoryRequest.builder()
        .quantity(orderedItem.getQuantity())
        .skuCode(orderedItem.getSkuCode())
        .build();
  }

  private Item mapOrderRequestToOrder(OrderedItem orderedItem) {
    return Item.builder()
        .quantity(orderedItem.getQuantity())
        .price(orderedItem.getPrice())
        .skuCode(orderedItem.getSkuCode())
        .build();
  }
}

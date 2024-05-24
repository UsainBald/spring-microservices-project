package com.practice.order.repositories;

import com.practice.order.models.Order;
import lombok.Data;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}

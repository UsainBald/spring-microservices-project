package com.practice.inventory.repositories;

import com.practice.inventory.modules.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

  Optional<Inventory> findBySkuCode(String skuCode);
}

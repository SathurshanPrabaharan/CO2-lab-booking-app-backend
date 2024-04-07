package com.inventoryservice.Repositories;

import com.inventoryservice.Models.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.UUID;

public interface InventoryRepository extends JpaRepository<Inventory, UUID> {

}

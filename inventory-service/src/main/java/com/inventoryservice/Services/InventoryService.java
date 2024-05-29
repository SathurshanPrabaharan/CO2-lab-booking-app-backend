package com.inventoryservice.Services;

import com.inventoryservice.DTO.Request.InventoryCreateRequest;
import com.inventoryservice.DTO.Request.InventoryUpdateRequest;
import com.inventoryservice.Exception.InventoryNotFoundException;
import com.inventoryservice.Models.Inventory;

import java.util.UUID;

public interface InventoryService {

    Inventory saveInventory(InventoryCreateRequest inventoryRequest);

    Inventory getInventory(UUID id) throws InventoryNotFoundException;

    Inventory updateInventory(UUID id, InventoryUpdateRequest inventoryUpdateRequest) throws InventoryNotFoundException;


//    List<Inventory> getAllInventories();
//    Inventory getInventoryById(long id);
//    Inventory updateInventory(Inventory inventory, long id);
//    void deleteInventory(long id);
}

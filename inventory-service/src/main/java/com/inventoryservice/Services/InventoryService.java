package com.inventoryservice.Services;

import com.inventoryservice.DTO.Request.Inventory.InventoryCreateRequest;
import com.inventoryservice.DTO.Request.Inventory.InventoryDeleteRequest;
import com.inventoryservice.DTO.Request.Inventory.InventoryUpdateRequest;
import com.inventoryservice.Enums.STATUS;
import com.inventoryservice.Exception.ResourceNotFoundException;
import com.inventoryservice.Models.Inventory;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface InventoryService {

    Inventory saveInventory(InventoryCreateRequest inventoryCreateRequest);

    Inventory findById(UUID id);

    List<Inventory> getAllInventory();

    Inventory updateInventory(UUID id, InventoryUpdateRequest inventoryUpdateRequest) throws ResourceNotFoundException;

    String deleteInventory(UUID id, @Valid InventoryDeleteRequest inventoryDeleteRequest) throws ResourceNotFoundException;


    Page<Inventory> filterInventory(String manufacturer, String processor, String memoryType, String memorySize, String storageType, String storageSize, String operatingSystem, STATUS status, LocalDate startWarrantyExpiryDate, LocalDate endWarrantyExpiryDate, LocalDate startNextMaintenanceDate, LocalDate endNextMaintenanceDate, LocalDate startLastMaintenanceDate, LocalDate endLastMaintenanceDate, int page, int size,UUID software);


}

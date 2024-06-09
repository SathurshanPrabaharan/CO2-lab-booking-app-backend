package com.inventoryservice.Services;

import com.inventoryservice.DTO.Request.Inventory.InventoryArchiveRequest;
import com.inventoryservice.DTO.Request.Inventory.InventoryCreateRequest;
import com.inventoryservice.DTO.Request.Inventory.InventoryUpdateRequest;
import com.inventoryservice.Enums.STATUS;
import com.inventoryservice.Exception.ResourceNotFoundException;
import com.inventoryservice.Models.Inventory;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface InventoryService {

    Inventory saveInventory(InventoryCreateRequest inventoryCreateRequest);

    List<Inventory> getAllInventory();
    Page<Inventory> filterInventory(String manufacturer, String model, String processor, String memoryType, String memorySize, String storageType, String storageSize, String operatingSystem, STATUS status, LocalDate startWarrantyExpiryDate, LocalDate endWarrantyExpiryDate, LocalDate startNextMaintenanceDate, LocalDate endNextMaintenanceDate, LocalDate startLastMaintenanceDate, LocalDate endLastMaintenanceDate, int page, int size,UUID software);

    Inventory findById(UUID id);


    Inventory updateInventory(UUID id, InventoryUpdateRequest request);

    void archiveInventory(UUID id, InventoryArchiveRequest request);




}

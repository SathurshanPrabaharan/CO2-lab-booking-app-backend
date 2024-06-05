package com.inventoryservice.Services;

import com.inventoryservice.DTO.Request.Inventory.InventoryCreateRequest;
import com.inventoryservice.DTO.Request.Inventory.InventoryUpdateRequest;
import com.inventoryservice.Enums.STATUS;
import com.inventoryservice.Exception.InventoryNotFoundException;
import com.inventoryservice.Models.Inventory;
import com.inventoryservice.Models.Software;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface InventoryService {

    Inventory saveInventory(InventoryCreateRequest inventoryCreateRequest);

    Inventory findById(UUID id);

    List<Inventory> getAllInventory();

    Inventory updateInventory(UUID id, InventoryUpdateRequest inventoryUpdateRequest) throws InventoryNotFoundException;

    List<Inventory> getInventoryWarrantyExpiryDateRange(LocalDate startDate, LocalDate endDate);

    List<Inventory> getNextMaintenanceDateRange(LocalDate startDate, LocalDate endDate);

    List<Inventory> getLastMaintenanceDateRange(LocalDate startDate, LocalDate endDate);

    Page<Inventory> filterInventory(String manufacturer, String processor, String memoryType, String memorySize, String storageType, String storageSize, String operatingSystem, STATUS status, LocalDate startWarrantyExpiryDate, LocalDate endWarrantyExpiryDate, LocalDate startNextMaintenanceDate, LocalDate endNextMaintenanceDate, LocalDate startLastMaintenanceDate, LocalDate endLastMaintenanceDate, int page, int size,UUID software);


//    List<Inventory> getAllInventories();
//    Inventory getInventoryById(long id);
//    Inventory updateInventory(Inventory inventory, long id);
//    void deleteInventory(long id);
}

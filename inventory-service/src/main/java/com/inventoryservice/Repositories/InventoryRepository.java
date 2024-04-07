package com.inventoryservice.Repositories;

import com.inventoryservice.Enums.STATUS;
import com.inventoryservice.Models.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;


import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface InventoryRepository extends JpaRepository<Inventory, UUID> {
    List<Inventory> findByLastMaintenanceDate(LocalDate maintenanceDate);
    List<Inventory> findByName(String name);
    List<Inventory> findByManufacturer(String manufacturer);

    List<Inventory> findByModel(String model);

    List<Inventory> findByProcessor(String processor);

    List<Inventory> findByMemoryType(String memoryType);

    List<Inventory> findByMemorySize(String memorySize);
    List<Inventory> findByStorageType(String storageType);

    List<Inventory> findByStorageSize(String storageSize);

    List<Inventory> findByOperatingSystem(String operatingSystem);

    List<Inventory> findByStatus(STATUS status);

    List<Inventory> findByPurchaseDate(LocalDate purchaseDate);

    List<Inventory> findByWarrantyExpiry(LocalDate warrantyExpiry);

    List<Inventory> findByNextMaintenanceDate(LocalDate nextMaintenanceDate);

    List<Inventory> findByPurchaseCost(Float purchaseCost);

    List<Inventory> findByCreatedBy(Long createdBy);

    List<Inventory> findByInstalledSoftwares(List<Integer> installedSoftwares);

    List<Inventory> findBySerialNum(String serialNum);

}

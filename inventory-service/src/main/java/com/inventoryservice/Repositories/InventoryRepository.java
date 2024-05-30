package com.inventoryservice.Repositories;

import com.inventoryservice.Enums.STATUS;
import com.inventoryservice.Models.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface InventoryRepository extends JpaRepository<Inventory, UUID> {
    List<Inventory> findByManufacturer(String manufacturer);

    List<Inventory> findByProcessor(String processor);

    List<Inventory> findByMemoryType(String memoryType);

    List<Inventory> findByMemorySize(String memorySize);

    List<Inventory> findByStorageType(String storageType);

    List<Inventory> findByStorageSize(String storageSize);

    List<Inventory> findByOperatingSystem(String operatingSystem);

    List<Inventory> findByStatus(STATUS status);

    @Query("SELECT e FROM Inventory e WHERE e.warrantyExpiry BETWEEN :startDate AND :endDate")
    List<Inventory> findByWarrantyExpiry(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT e FROM Inventory e WHERE e.nextMaintenanceDate BETWEEN :startDate AND :endDate")
    List<Inventory> findByNextMaintenanceDate(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT e FROM Inventory e WHERE e.lastMaintenanceDate BETWEEN :startDate AND :endDate")
    List<Inventory> findByLastMaintenanceDate(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);


    List<Inventory> findByInstalledSoftwares(List<Integer> installedSoftwares);


}

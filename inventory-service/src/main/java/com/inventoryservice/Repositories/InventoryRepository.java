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


    @Query("SELECT e FROM Inventory e WHERE e.warrantyExpiry BETWEEN :startDate AND :endDate")
    List<Inventory> findByWarrantyExpiry(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT e FROM Inventory e WHERE e.nextMaintenanceDate BETWEEN :startDate AND :endDate")
    List<Inventory> findByNextMaintenanceDate(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT e FROM Inventory e WHERE e.lastMaintenanceDate BETWEEN :startDate AND :endDate")
    List<Inventory> findByLastMaintenanceDate(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    List<Inventory> findAllByOrderByCreatedAtDesc();

}

package com.inventoryservice.Repositories;

import com.inventoryservice.Models.Inventory;
import com.inventoryservice.Models.Software;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface InventoryRepository extends JpaRepository<Inventory, UUID> {


    List<Inventory> findAllByOrderByCreatedAtDesc();

    @Query("SELECT i.installedSoftwares FROM Inventory i WHERE i.id = :inventoryId")
    Set<Software> findInstalledSoftwaresByInventoryId(@Param("inventoryId") UUID inventoryId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM inventory_software WHERE inventory_id = ?1", nativeQuery = true)
    void deleteSoftwareByInventoryId(UUID inventoryId);

}

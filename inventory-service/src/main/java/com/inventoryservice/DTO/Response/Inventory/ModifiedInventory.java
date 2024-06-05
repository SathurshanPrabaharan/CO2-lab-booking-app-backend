package com.inventoryservice.DTO.Response.Inventory;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.inventoryservice.Enums.STATUS;
import com.inventoryservice.Models.Inventory;
import com.inventoryservice.Models.Software;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ModifiedInventory {

    private UUID id;
    private String name;
    private String serialNum;
    private String manufacturer;
    private String model;
    private String processor;
    private String memoryType;
    private String memorySize;
    private String storageType;
    private String storageSize;
    private String operatingSystem;
    private STATUS status;
    private LocalDate purchaseDate;
    private Float purchaseCost;
    private LocalDate warrantyExpiry;
    private String shortNote;
    private LocalDate lastMaintenanceDate;
    private LocalDate nextMaintenanceDate;
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Long createdBy;
    private List<Software> installedSoftwares;



    public ModifiedInventory (Inventory inventory){
        this.id=inventory.getId();
        this.name=inventory.getName();
        this.serialNum=inventory.getSerialNum();
        this.manufacturer=inventory.getManufacturer();
        this.model=inventory.getModel();
        this.processor=inventory.getProcessor();
        this.memoryType=inventory.getMemoryType();
        this.memorySize=inventory.getMemorySize();
        this.storageType=inventory.getStorageType();
        this.storageSize=inventory.getStorageSize();
        this.operatingSystem=inventory.getOperatingSystem();
        this.status=inventory.getStatus();
        this.purchaseDate=inventory.getPurchaseDate();
        this.warrantyExpiry=inventory.getWarrantyExpiry();
        this.shortNote=inventory.getShortNote();
        this.lastMaintenanceDate=inventory.getLastMaintenanceDate();
        this.nextMaintenanceDate=inventory.getNextMaintenanceDate();
        this.createdAt=inventory.getCreatedAt();
        this.updatedAt=inventory.getUpdatedAt();
        this.createdBy=inventory.getCreatedBy();
        this.installedSoftwares   = inventory.getInstalledSoftwares() != null ? inventory.getInstalledSoftwares() : new ArrayList<>();
        // Initialize installedSoftwares list and copy elements
        this.installedSoftwares = new ArrayList<>();
        List<Software> inventorySoftwares = inventory.getInstalledSoftwares();
        if (inventorySoftwares != null) {
            this.installedSoftwares.addAll(inventorySoftwares);
        }
    }


}

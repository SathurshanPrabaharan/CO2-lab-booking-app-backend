package com.inventoryservice.DTO.Response.Inventory;

import com.inventoryservice.DTO.Response.Software.ModifiedSoftware;
import com.inventoryservice.Enums.STATUS;
import com.inventoryservice.Models.Inventory;
import com.inventoryservice.Models.Software;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class InventoryCreatedResponse {

    private String message;
    private ResponseCreatedInventory data;


    public InventoryCreatedResponse(String message, Inventory foundedInventory) {
        this.message = message;
        this.data = new ResponseCreatedInventory(foundedInventory);
    }
}

@AllArgsConstructor
@NoArgsConstructor
@Data
class ResponseCreatedInventory {
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
    private LocalDate purchaseDate;
    private Float purchaseCost;
    private LocalDate warrantyExpiry;
    private String shortNote;
    private LocalDate lastMaintenanceDate;
    private LocalDate nextMaintenanceDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID createdBy;
    private STATUS status;
    private Set<ModifiedSoftware> installedSoftwares = new HashSet<>();

    public ResponseCreatedInventory(Inventory inventory) {
        this.id = inventory.getId();
        this.name = inventory.getName();
        this.serialNum = inventory.getSerialNum();
        this.manufacturer = inventory.getManufacturer();
        this.model = inventory.getModel();
        this.processor = inventory.getProcessor();
        this.memoryType = inventory.getMemoryType();
        this.memorySize = inventory.getMemorySize();
        this.storageType = inventory.getStorageType();
        this.storageSize = inventory.getStorageSize();
        this.operatingSystem = inventory.getOperatingSystem();
        this.purchaseDate = inventory.getPurchaseDate();
        this.purchaseCost = inventory.getPurchaseCost();
        this.warrantyExpiry = inventory.getWarrantyExpiry();
        this.shortNote = inventory.getShortNote();
        this.lastMaintenanceDate = inventory.getLastMaintenanceDate();
        this.nextMaintenanceDate = inventory.getNextMaintenanceDate();
        this.createdAt = inventory.getCreatedAt();
        this.updatedAt = inventory.getUpdatedAt();
        this.createdBy = inventory.getCreatedBy();
        this.status = inventory.getStatus();


        // if installed softwares exist
        Set<Software> temp = inventory.getInstalledSoftwares();
        if (temp != null) {
            for (Software software : temp) {
                this.installedSoftwares.add(new ModifiedSoftware(software));
            }
        }


    }
}
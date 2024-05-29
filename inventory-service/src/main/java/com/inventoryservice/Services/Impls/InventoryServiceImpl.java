package com.inventoryservice.Services.Impls;

import com.inventoryservice.DTO.Request.InventoryCreateRequest;
import com.inventoryservice.DTO.Request.InventoryUpdateRequest;
import com.inventoryservice.Exception.InventoryNotFoundException;
import com.inventoryservice.Models.Inventory;
import com.inventoryservice.Repositories.InventoryRepository;
import com.inventoryservice.Services.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;

    @Override
    public Inventory saveInventory(InventoryCreateRequest inventoryRequest) {
        Inventory inventory = Inventory.build(UUID.randomUUID(), inventoryRequest.getName(), inventoryRequest.getSerialNum(), inventoryRequest.getManufacturer(), inventoryRequest.getModel(),
                inventoryRequest.getProcessor(), inventoryRequest.getMemoryType(), inventoryRequest.getMemorySize(), inventoryRequest.getStorageType(),
                inventoryRequest.getStorageSize(), inventoryRequest.getOperatingSystem(), inventoryRequest.getStatus(), inventoryRequest.getPurchaseDate(),
                inventoryRequest.getPurchaseCost(), inventoryRequest.getWarrantyExpiry(), inventoryRequest.getShortNote(), inventoryRequest.getLastMaintenanceDate(),
                inventoryRequest.getNextMaintenanceDate(), inventoryRequest.getCreatedAt(), inventoryRequest.getUpdatedAt(), inventoryRequest.getCreatedBy(),
                inventoryRequest.getInstalledSoftwares());
        return inventoryRepository.save(inventory);
    }

    public Inventory getInventory(UUID id) throws InventoryNotFoundException {
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new InventoryNotFoundException("Inventory not found with ID: " + id));
        return inventory;

    }

    @Override
    public Inventory updateInventory(UUID id, InventoryUpdateRequest inventoryDetails) {


        Inventory existingInventory = inventoryRepository.findById(id)
                .orElseThrow(
                        () -> new InventoryNotFoundException("Inventory not found with ID: " + id)
                );

        Inventory updateInventory = Inventory.builder()
                .id(existingInventory.getId())
                .name(existingInventory.getName())
                .serialNum(existingInventory.getSerialNum())
                .manufacturer(existingInventory.getManufacturer())
                .model(existingInventory.getModel())
                .processor(existingInventory.getProcessor())
                .memoryType(existingInventory.getMemoryType())
                .memorySize(existingInventory.getMemorySize())
                .storageSize(existingInventory.getStorageSize())
                .operatingSystem(existingInventory.getOperatingSystem())
                .status(existingInventory.getStatus())
                .purchaseDate(existingInventory.getPurchaseDate())
                .purchaseCost(existingInventory.getPurchaseCost())
                .warrantyExpiry(existingInventory.getWarrantyExpiry())
                .shortNote(existingInventory.getShortNote())
                .lastMaintenanceDate(existingInventory.getLastMaintenanceDate())
                .nextMaintenanceDate(existingInventory.getNextMaintenanceDate())
                .createdAt(existingInventory.getCreatedAt())
                .updatedAt(existingInventory.getUpdatedAt())
                .createdBy(existingInventory.getCreatedBy())
                .installedSoftwares(existingInventory.getInstalledSoftwares())
                .build();
        return inventoryRepository.save(updateInventory);

    }
}
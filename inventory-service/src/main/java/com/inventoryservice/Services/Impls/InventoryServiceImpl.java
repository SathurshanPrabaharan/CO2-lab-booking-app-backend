package com.inventoryservice.Services.Impls;

import com.inventoryservice.DTO.Request.InventoryCreateRequest;
import com.inventoryservice.DTO.Request.InventoryUpdateRequest;
import com.inventoryservice.Enums.STATUS;
import com.inventoryservice.Exception.InventoryNotFoundException;
import com.inventoryservice.Models.Inventory;
import com.inventoryservice.Repositories.InventoryRepository;
import com.inventoryservice.Services.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;

    @Override
    public Inventory saveInventory(InventoryCreateRequest inventoryCreateRequest) {
        Inventory inventory = Inventory.builder()
                .id(UUID.randomUUID())
                .name(inventoryCreateRequest.getName())
                .serialNum(inventoryCreateRequest.getSerialNum())
                .manufacturer(inventoryCreateRequest.getManufacturer())
                .model(inventoryCreateRequest.getModel())
                .processor(inventoryCreateRequest.getProcessor())
                .memoryType(inventoryCreateRequest.getMemoryType())
                .memorySize(inventoryCreateRequest.getMemorySize())
                .storageSize(inventoryCreateRequest.getStorageSize())
                .operatingSystem(inventoryCreateRequest.getOperatingSystem())
                .status(STATUS.ACTIVE)
                .purchaseDate(inventoryCreateRequest.getPurchaseDate())
                .purchaseCost(inventoryCreateRequest.getPurchaseCost())
                .warrantyExpiry(inventoryCreateRequest.getWarrantyExpiry())
                .shortNote(inventoryCreateRequest.getShortNote())
                .lastMaintenanceDate(inventoryCreateRequest.getLastMaintenanceDate())
                .nextMaintenanceDate(inventoryCreateRequest.getNextMaintenanceDate())
                .createdAt(inventoryCreateRequest.getCreatedAt())
                .updatedAt(inventoryCreateRequest.getUpdatedAt())
                .createdBy(inventoryCreateRequest.getCreatedBy())
                .installedSoftwares(inventoryCreateRequest.getInstalledSoftwares())
                .build();
        return inventoryRepository.save(inventory);
    }


    @Override
    public Inventory findById(UUID id) {
        Optional<Inventory> AdminOptional = inventoryRepository.findById(id);
        return AdminOptional.orElseThrow(() -> new InventoryNotFoundException("admin not found with id : " + id));
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
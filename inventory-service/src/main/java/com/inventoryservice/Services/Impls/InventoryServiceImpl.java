package com.inventoryservice.Services.Impls;

import com.inventoryservice.DTO.Request.Inventory.InventoryCreateRequest;
import com.inventoryservice.DTO.Request.Inventory.InventoryUpdateRequest;
import com.inventoryservice.Enums.STATUS;
import com.inventoryservice.Exception.InventoryNotFoundException;
import com.inventoryservice.Models.Inventory;
import com.inventoryservice.Models.Software;
import com.inventoryservice.Repositories.InventoryRepository;
import com.inventoryservice.Services.InventoryService;
import com.inventoryservice.Services.SoftwareService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final SoftwareService softwareService;

    @Override
    public Inventory saveInventory(InventoryCreateRequest inventoryCreateRequest) {
        List<Software> associatedSoftwareList = new ArrayList<>();
        for (UUID softwareId : inventoryCreateRequest.getInstalledSoftwaresID()) {
            Software associatedSoftware = softwareService.findById(softwareId);
            associatedSoftwareList.add(associatedSoftware);
        }
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
                .status(STATUS.ACTIVE) //set default status
                .purchaseDate(inventoryCreateRequest.getPurchaseDate())
                .purchaseCost(inventoryCreateRequest.getPurchaseCost())
                .warrantyExpiry(inventoryCreateRequest.getWarrantyExpiry())
                .shortNote(inventoryCreateRequest.getShortNote())
                .lastMaintenanceDate(inventoryCreateRequest.getLastMaintenanceDate())
                .nextMaintenanceDate(inventoryCreateRequest.getNextMaintenanceDate())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .createdBy(inventoryCreateRequest.getCreatedBy())
                .installedSoftwares(associatedSoftwareList)
                .build();
        return inventoryRepository.save(inventory);
    }

    @Override
    public Inventory findById(UUID id) {
        Optional<Inventory> InventoryOptional = inventoryRepository.findById(id);
        return InventoryOptional.orElseThrow(() -> new InventoryNotFoundException("Inventory not found with id : " + id));
    }
    @Override
    public List<Inventory> getAllInventory(){
        return inventoryRepository.findAll();
    }

    @Override
    public Inventory updateInventory(UUID id, InventoryUpdateRequest inventoryUpdateRequest) {


        Inventory existingInventory = inventoryRepository.findById(id)
                .orElseThrow(
                        () -> new InventoryNotFoundException("Inventory not found with ID: " + id)
                );

        List<Software> associatedSoftwareList = new ArrayList<>();
        for (UUID softwareId : inventoryUpdateRequest.getInstalledSoftwaresID()) {
            Software associatedSoftware = softwareService.findById(softwareId);
            associatedSoftwareList.add(associatedSoftware);
        }
        Inventory updateInventory = Inventory.builder()
                .id(existingInventory.getId())
                .name(inventoryUpdateRequest.getName())
                .serialNum(existingInventory.getSerialNum())
                .manufacturer(existingInventory.getManufacturer())
                .model(existingInventory.getModel())
                .processor(existingInventory.getProcessor())
                .memoryType(existingInventory.getMemoryType())
                .memorySize(existingInventory.getMemorySize())
                .storageSize(existingInventory.getStorageSize())
                .operatingSystem(existingInventory.getOperatingSystem())
                .status(STATUS.valueOf(inventoryUpdateRequest.getStatus().toUpperCase()))
                .purchaseDate(existingInventory.getPurchaseDate())
                .purchaseCost(existingInventory.getPurchaseCost())
                .warrantyExpiry(existingInventory.getWarrantyExpiry())
                .shortNote(existingInventory.getShortNote())
                .lastMaintenanceDate(existingInventory.getLastMaintenanceDate())
                .nextMaintenanceDate(existingInventory.getNextMaintenanceDate())
                .createdAt(existingInventory.getCreatedAt())
                .updatedAt(existingInventory.getUpdatedAt())
                .createdBy(existingInventory.getCreatedBy())
                .installedSoftwares(associatedSoftwareList)
                .build();
        return inventoryRepository.save(updateInventory);

    }

    public List<Inventory> getInventoryWarrantyExpiryDateRange(LocalDate startDate, LocalDate endDate){
        return inventoryRepository.findByWarrantyExpiry(startDate,endDate);
    }
    public List<Inventory> getNextMaintenanceDateRange(LocalDate startDate, LocalDate endDate){
        return inventoryRepository.findByNextMaintenanceDate(startDate,endDate);
    }
    public List<Inventory> getLastMaintenanceDateRange(LocalDate startDate, LocalDate endDate){
        return inventoryRepository.findByLastMaintenanceDate(startDate,endDate);
    }



}
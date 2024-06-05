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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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
    @Override
    public Page<Inventory> filterInventory(String manufacturer, String processor, String memoryType, String memorySize, String storageType, String storageSize, String operatingSystem, STATUS status, LocalDate startWarrantyExpiryDate, LocalDate endWarrantyExpiryDate, LocalDate startNextMaintenanceDate, LocalDate endNextMaintenanceDate, LocalDate startLastMaintenanceDate, LocalDate endLastMaintenanceDate, int page, int size, UUID softwareId) {
        if (page < 0 || size <= 0) {
            throw new IllegalArgumentException("Page index must not be less than zero and size must be greater than zero");
        }

        List<Inventory> allInventory = inventoryRepository.findAllByOrderByCreatedAtDesc();
        List<Inventory> filteredInventory = allInventory.stream()
                .filter(inventory -> manufacturer == null || (inventory.getManufacturer() != null && inventory.getManufacturer().equals(manufacturer)))
                .filter(inventory -> processor == null || (inventory.getProcessor() != null && inventory.getProcessor().equals(processor)))
                .filter(inventory -> memoryType == null || (inventory.getMemoryType() != null && inventory.getMemoryType().equals(memoryType)))
                .filter(inventory -> memorySize == null || (inventory.getMemorySize() != null && inventory.getMemorySize().equals(memorySize)))
                .filter(inventory -> storageType == null || (inventory.getStorageType() != null && inventory.getStorageType().equals(storageType)))
                .filter(inventory -> storageSize == null || (inventory.getStorageSize() != null && inventory.getStorageSize().equals(storageSize)))
                .filter(inventory -> operatingSystem == null || (inventory.getOperatingSystem() != null && inventory.getOperatingSystem().equals(operatingSystem)))
                .filter(inventory -> status == null || (inventory.getStatus() != null && inventory.getStatus().equals(status)))
                .filter(inventory -> startWarrantyExpiryDate == null || (inventory.getWarrantyExpiry() != null && (inventory.getWarrantyExpiry().isEqual(startWarrantyExpiryDate) || inventory.getWarrantyExpiry().isAfter(startWarrantyExpiryDate))))
                .filter(inventory -> endWarrantyExpiryDate == null || (inventory.getWarrantyExpiry() != null && (inventory.getWarrantyExpiry().isEqual(endWarrantyExpiryDate) || inventory.getWarrantyExpiry().isBefore(endWarrantyExpiryDate))))
                .filter(inventory -> startNextMaintenanceDate == null || (inventory.getNextMaintenanceDate() != null && (inventory.getNextMaintenanceDate().isEqual(startNextMaintenanceDate) || inventory.getNextMaintenanceDate().isAfter(startNextMaintenanceDate))))
                .filter(inventory -> endNextMaintenanceDate == null || (inventory.getNextMaintenanceDate() != null && (inventory.getNextMaintenanceDate().isEqual(endNextMaintenanceDate) || inventory.getNextMaintenanceDate().isBefore(endNextMaintenanceDate))))
                .filter(inventory -> startLastMaintenanceDate == null || (inventory.getLastMaintenanceDate() != null && (inventory.getLastMaintenanceDate().isEqual(startLastMaintenanceDate) || inventory.getLastMaintenanceDate().isAfter(startLastMaintenanceDate))))
                .filter(inventory -> endLastMaintenanceDate == null || (inventory.getLastMaintenanceDate() != null && (inventory.getLastMaintenanceDate().isEqual(endLastMaintenanceDate) || inventory.getLastMaintenanceDate().isBefore(endLastMaintenanceDate))))
                .filter(inventory -> softwareId == null || (inventory.getInstalledSoftwares() != null && inventory.getInstalledSoftwares().stream().anyMatch(software -> software.getId().equals(softwareId))))
                .collect(Collectors.toList());

        int totalSize = filteredInventory.size();
        int start = Math.min(page * size, totalSize);
        int end = Math.min((page + 1) * size, totalSize);

        List<Inventory> paginatedList = filteredInventory.subList(start, end);

        return new PageImpl<>(paginatedList, PageRequest.of(page, size), totalSize);
    }


}
package com.inventoryservice.Services.Impls;

import com.inventoryservice.DTO.Request.Inventory.InventoryArchiveRequest;
import com.inventoryservice.DTO.Request.Inventory.InventoryCreateRequest;
import com.inventoryservice.DTO.Request.Inventory.InventoryUpdateRequest;
import com.inventoryservice.Enums.STATUS;
import com.inventoryservice.Exception.ResourceNotFoundException;
import com.inventoryservice.Models.Inventory;
import com.inventoryservice.Models.Software;
import com.inventoryservice.Repositories.InventoryRepository;
import com.inventoryservice.Repositories.SoftwareRepository;
import com.inventoryservice.Services.InventoryService;
import com.inventoryservice.Services.SoftwareService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final SoftwareRepository softwareRepository;
    private final SoftwareService softwareService;

    @Override
    public Inventory saveInventory(InventoryCreateRequest request) {

        Inventory inventory = Inventory.builder()
                .id(UUID.randomUUID())
                .name(request.getName())
                .serialNum(request.getSerialNum())
                .manufacturer(request.getManufacturer())
                .model(request.getModel())
                .processor(request.getProcessor())
                .memoryType(request.getMemoryType())
                .memorySize(request.getMemorySize())
                .storageSize(request.getStorageSize())
                .storageType(request.getStorageType())
                .operatingSystem(request.getOperatingSystem())
                .status(STATUS.ACTIVE) //set default status
                .purchaseDate(request.getPurchaseDate())
                .purchaseCost(request.getPurchaseCost())
                .warrantyExpiry(request.getWarrantyExpiry())
                .shortNote(request.getShortNote())
                .lastMaintenanceDate(request.getLastMaintenanceDate())
                .nextMaintenanceDate(request.getNextMaintenanceDate())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .createdBy(request.getCreatedBy())
                .build();

        // Set Installed softwares if exists
        if (request.getInstalledSoftwareIds() != null) {
            Set<UUID> installedSoftwareIds = request.getInstalledSoftwareIds();
            List<Software> installedSoftwareList = softwareRepository.findAllById(installedSoftwareIds);

            // Validate if all installed software IDs were found
            if (installedSoftwareList.size() != installedSoftwareIds.size()) {
                throw new IllegalArgumentException ("One or more Installed Software IDs not found");
            }

            // Update Inventory's installed softwares
            inventory.getInstalledSoftwares().addAll(installedSoftwareList);
        }

        return inventoryRepository.save(inventory);
    }

    @Override
    public List<Inventory> getAllInventory(){
        return inventoryRepository.findAll();
    }


    @Override
    public Page<Inventory> filterInventory(String manufacturer, String model, String processor, String memoryType, String memorySize, String storageType, String storageSize, String operatingSystem, STATUS status, LocalDate startWarrantyExpiryDate, LocalDate endWarrantyExpiryDate, LocalDate startNextMaintenanceDate, LocalDate endNextMaintenanceDate, LocalDate startLastMaintenanceDate, LocalDate endLastMaintenanceDate, int page, int size, UUID softwareId) {

        List<Inventory> allInventory = inventoryRepository.findAllByOrderByCreatedAtDesc();
        List<Inventory> filteredInventory = allInventory.stream()
                .filter(inventory -> manufacturer == null || (inventory.getManufacturer() != null && inventory.getManufacturer().equals(manufacturer)))
                .filter(inventory -> model == null || (inventory.getModel() != null && inventory.getModel().equals(model)))
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




    @Override
    public Inventory findById(UUID id) {
        Optional<Inventory> inventoryOptional = inventoryRepository.findById(id);
        Inventory foundedInventory = inventoryOptional.orElseThrow(
                () -> new ResourceNotFoundException("Inventory not found with id : " + id)
        );
        Set<Software> installedSoftwares = inventoryRepository.findInstalledSoftwaresByInventoryId(id);
        foundedInventory.setInstalledSoftwares(installedSoftwares);
        return foundedInventory;

    }



    @Override
    public Inventory updateInventory(UUID id, InventoryUpdateRequest request) {


        //get inventory
        Inventory existingInventory = findById(id);


        // Update mutable fields from the request
        existingInventory.setName(request.getName());
        existingInventory.setShortNote(request.getShortNote());
        existingInventory.setSerialNum(request.getSerialNum());
        existingInventory.setManufacturer(request.getManufacturer());
        existingInventory.setModel(request.getModel());
        existingInventory.setProcessor(request.getProcessor());
        existingInventory.setMemoryType(request.getMemoryType());
        existingInventory.setMemorySize(request.getMemorySize());
        existingInventory.setStorageType(request.getStorageType());
        existingInventory.setStorageSize(request.getStorageSize());
        existingInventory.setOperatingSystem(request.getOperatingSystem());
        existingInventory.setPurchaseDate(request.getPurchaseDate());
        existingInventory.setPurchaseCost(request.getPurchaseCost());
        existingInventory.setWarrantyExpiry(request.getWarrantyExpiry());
        existingInventory.setLastMaintenanceDate(request.getLastMaintenanceDate());
        existingInventory.setNextMaintenanceDate(request.getNextMaintenanceDate());
        existingInventory.setUpdatedBy(request.getUpdatedBy());
        existingInventory.setUpdatedAt(LocalDateTime.now());
        existingInventory.setStatus(STATUS.valueOf(request.getStatus().toUpperCase()));



        // Delete existing entries from inventory_softwares table related to the inventory
        inventoryRepository.deleteSoftwareByInventoryId(existingInventory.getId());

        // Update Installed Softwares if provided
        if (request.getInstalledSoftwareIds() != null && !request.getInstalledSoftwareIds().isEmpty()) {
            List<Software> installedSoftwareList = softwareRepository.findAllById(request.getInstalledSoftwareIds());

            // Find missing IDs
            Set<UUID> foundIds = installedSoftwareList.stream()
                    .map(Software::getId)
                    .collect(Collectors.toSet());
            Set<UUID> missingIds = request.getInstalledSoftwareIds().stream()
                    .filter(softwareId -> !foundIds.contains(softwareId))
                    .collect(Collectors.toSet());

            // Validate if all installed software IDs were found
            if (!missingIds.isEmpty()) {
                throw new IllegalArgumentException("One or more Installed Software IDs not found: " + missingIds);
            }

            // Set the new installed softwares
            existingInventory.setInstalledSoftwares(new HashSet<>(installedSoftwareList));
        } else {
            // If no installed software IDs are provided, clear the installed softwares
            existingInventory.getInstalledSoftwares().clear();
        }


        return inventoryRepository.save(existingInventory);

    }


    @Override
    public void archiveInventory(UUID id, InventoryArchiveRequest request) {

        Inventory existingInventory = findById(id);

        if(existingInventory.getStatus()==STATUS.ARCHIVED){
            throw new ResourceNotFoundException("Invalid : Inventory already archived");
        }

        //set the values
        existingInventory.setStatus(STATUS.ARCHIVED);
        existingInventory.setUpdatedBy(request.getArchivedBy());
        existingInventory.setUpdatedAt(LocalDateTime.now());


        inventoryRepository.save(existingInventory);

    }



}
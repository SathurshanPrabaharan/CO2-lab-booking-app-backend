package com.inventoryservice.Controllers;

import com.inventoryservice.DTO.Request.Inventory.InventoryCreateRequest;
import com.inventoryservice.DTO.Request.Inventory.InventoryUpdateRequest;
import com.inventoryservice.DTO.Response.Inventory.InventoryDetailsResponse;
import com.inventoryservice.DTO.Response.Inventory.InventoryResponse;
import com.inventoryservice.Enums.STATUS;
import com.inventoryservice.Exception.InventoryNotFoundException;
import com.inventoryservice.Models.Inventory;
import com.inventoryservice.Repositories.InventoryRepository;
import com.inventoryservice.Services.InventoryService;

import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/inventories")
public class InventoryController {
    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        super();
        this.inventoryService = inventoryService;
    }

    //create done
    @PostMapping
    public ResponseEntity<Object> saveInventory(@RequestBody @Valid InventoryCreateRequest inventory) {
        Inventory savedInventory = inventoryService.saveInventory(inventory);
        String message = "Inventory created successfully";
        InventoryResponse response = new InventoryResponse(message, savedInventory);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Object> getAllInventory(
            @RequestParam(required = false) String manufacturer,
            @RequestParam(required = false) String model,
            @RequestParam(required = false) String processor,
            @RequestParam(required = false) String memoryType,
            @RequestParam(required = false) String memorySize,
            @RequestParam(required = false) String storageType,
            @RequestParam(required = false) String storageSize,
            @RequestParam(required = false) String operatingSystem,
            @RequestParam(required = false) STATUS status) {
        List<Inventory> inventoryList;
        if (manufacturer != null) {
            inventoryList = inventoryRepository.findByManufacturer(manufacturer);

        } else if (model != null) {
            inventoryList = inventoryRepository.findByModel(model);
        } else if (processor != null) {
            inventoryList = inventoryRepository.findByProcessor(processor);
        } else if (memoryType != null) {
            inventoryList = inventoryRepository.findByMemoryType(memoryType);
        } else if (memorySize != null) {
            inventoryList = inventoryRepository.findByMemorySize(memorySize);
        } else if (storageType != null) {
            inventoryList = inventoryRepository.findByStorageType(storageType);
        } else if (storageSize != null) {
            inventoryList = inventoryRepository.findByStorageSize(storageSize);
        } else if (operatingSystem != null) {
            inventoryList = inventoryRepository.findByOperatingSystem(operatingSystem);
        } else if (status != null) {
            inventoryList = inventoryRepository.findByStatus(status);
        } else {
            inventoryList = inventoryRepository.findAll();
        }
        if (inventoryList.isEmpty()) {
            throw new InventoryNotFoundException("No inventory found");
        }

        return ResponseEntity.ok(inventoryList);
    }

    //update done
    @PutMapping("{id}")
    public ResponseEntity<Object> updateInventory(@PathVariable UUID id, @RequestBody @Valid InventoryUpdateRequest inventoryUpdateRequest) throws InventoryNotFoundException {
        Inventory updateInventory = inventoryService.updateInventory(id, inventoryUpdateRequest);
        String message = "Inventory updated successfully";
        InventoryDetailsResponse response = new InventoryDetailsResponse(message, updateInventory);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteInventory(@PathVariable UUID id) throws InventoryNotFoundException {
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new InventoryNotFoundException("Inventory not found with id: " + id));

        inventoryRepository.delete(inventory);
        String message = "Inventory with ID: " + id + " has been deleted";
        return ResponseEntity.ok(message);
    }


    @GetMapping("{id}")
    public ResponseEntity<InventoryResponse> getInventoryDetails(@PathVariable UUID id) throws InventoryNotFoundException {
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new InventoryNotFoundException("Inventory not found with id : " + id));
        String message = "Inventory details retrieved successfully";
        InventoryResponse response = new InventoryResponse(message, inventory);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}


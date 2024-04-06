package com.inventoryservice.Controllers;

import com.inventoryservice.Models.Inventory;
import com.inventoryservice.Repositories.InventoryRepository;
import com.inventoryservice.Services.InventoryService;

import java.lang.module.ResolutionException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/inventories")
public class InventoryController {
    @Autowired
    private InventoryRepository inventoryRepository;

    private InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        super();
        this.inventoryService = inventoryService;
    }

    @PostMapping
    public ResponseEntity<Inventory> saveEmployee(@RequestBody Inventory inventory){
        return new ResponseEntity<Inventory>(inventoryService.saveInventory(inventory), HttpStatus.CREATED);
    }

    @GetMapping
    public List<Inventory> getAllInventory(){
        return inventoryRepository.findAll();
    }


    @GetMapping("{id}")
    public ResponseEntity<Inventory> getInventorybyId(@PathVariable UUID id) {
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventory not found with id " + id));
        return ResponseEntity.ok(inventory);
    }

    @PutMapping("{id}")
    public ResponseEntity<Inventory> updateInventory(@PathVariable UUID id ,@RequestBody Inventory inventoryDetails){

        Inventory updateInventory = inventoryRepository.findById(id).orElseThrow(
                ()->new ResolutionException("Inventory not found with id " +id)
        );

        updateInventory.setName(inventoryDetails.getName());
        updateInventory.setSerialNum(inventoryDetails.getSerialNum());
        updateInventory.setManufacturer(inventoryDetails.getManufacturer());
        updateInventory.setModel(inventoryDetails.getModel());
        updateInventory.setProcessor(inventoryDetails.getProcessor());
        updateInventory.setMemoryType(inventoryDetails.getMemoryType());
        updateInventory.setMemorySize(inventoryDetails.getMemorySize());
        updateInventory.setStorageType(inventoryDetails.getStorageType());
        updateInventory.setStorageSize(inventoryDetails.getStorageSize());
        updateInventory.setOperatingSystem(inventoryDetails.getOperatingSystem());
        updateInventory.setStatus(inventoryDetails.getStatus());
        updateInventory.setPurchaseDate(inventoryDetails.getPurchaseDate());
        updateInventory.setPurchaseCost(inventoryDetails.getPurchaseCost());
        updateInventory.setWarrantyExpiry(inventoryDetails.getWarrantyExpiry());
        updateInventory.setShortNote(inventoryDetails.getShortNote());
        updateInventory.setLastMaintenanceDate(inventoryDetails.getLastMaintenanceDate());
        updateInventory.setNextMaintenanceDate(inventoryDetails.getNextMaintenanceDate());
        updateInventory.setCreatedAt(inventoryDetails.getCreatedAt());
        updateInventory.setUpdatedAt(inventoryDetails.getUpdatedAt());
        updateInventory.setCreatedBy(inventoryDetails.getCreatedBy());
        updateInventory.setInstalledSoftwares(inventoryDetails.getInstalledSoftwares());
        inventoryRepository.save(updateInventory);

        return ResponseEntity.ok(updateInventory);
    }




}

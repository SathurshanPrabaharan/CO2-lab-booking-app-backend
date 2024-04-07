package com.inventoryservice.Controllers;

import com.inventoryservice.Enums.STATUS;
import com.inventoryservice.Exception.ResourceNotFoundException;
import com.inventoryservice.Models.Inventory;
import com.inventoryservice.Repositories.InventoryRepository;
import com.inventoryservice.Services.InventoryService;

import java.lang.module.ResolutionException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
    public ResponseEntity<Inventory> saveEmployee(@RequestBody Inventory inventory) {
        return new ResponseEntity<Inventory>(inventoryService.saveInventory(inventory), HttpStatus.CREATED);
    }

    @GetMapping
<<<<<<< HEAD
    public List<Inventory> getAllInventory() {
        return inventoryRepository.findAll();
    }


=======
    public List<Inventory> getAllInventory(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate lastMaintenanceDate,
                                           @RequestParam(required = false) String name,
                                           @RequestParam(required = false) String manufacturer,
                                           @RequestParam(required = false) String model,
                                           @RequestParam(required = false) String processor,
                                           @RequestParam(required = false) String memoryType,
                                           @RequestParam(required = false) String memorySize,
                                           @RequestParam(required = false) String storageType,
                                           @RequestParam(required = false) String storageSize,
                                           @RequestParam(required = false) String operatingSystem,
                                           @RequestParam(required = false) STATUS status,
                                           @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate purchaseDate,
                                           @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate warrantyExpiry,
                                           @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate nextMaintenanceDate,
                                           @RequestParam(required = false) Float purchaseCost,
                                           @RequestParam(required = false) Long createdBy,
                                           @RequestParam(required = false) List<Integer>installedSoftwares) {

        List<Inventory> inventoryList;
        if (lastMaintenanceDate != null) {
            inventoryList = inventoryRepository.findByLastMaintenanceDate(lastMaintenanceDate);
        }
        else if (name !=null) {
            inventoryList=inventoryRepository.findByName(name);

        }
        else if (manufacturer !=null) {
            inventoryList=inventoryRepository.findByManufacturer(manufacturer);

        }
        else if (model !=null) {
            inventoryList=inventoryRepository.findByModel(model);
        }
        else if (processor !=null) {
            inventoryList=inventoryRepository.findByProcessor(processor);
        }
        else if (memoryType !=null) {
            inventoryList=inventoryRepository.findByMemoryType(memoryType);
        }
        else if (memorySize !=null) {
            inventoryList=inventoryRepository.findByMemorySize(memorySize);
        }
        else if (storageType !=null) {
            inventoryList=inventoryRepository.findByStorageType(storageType);
        }
        else if (storageSize !=null) {
            inventoryList=inventoryRepository.findByStorageSize(storageSize);
        }
        else if (operatingSystem !=null) {
            inventoryList=inventoryRepository.findByOperatingSystem(operatingSystem);
        }
        else if (status != null) {
            inventoryList = inventoryRepository.findByStatus(status);
        }
        else if (purchaseDate !=null) {
            inventoryList=inventoryRepository.findByPurchaseDate(purchaseDate);
        }
        else if (warrantyExpiry !=null) {
            inventoryList=inventoryRepository.findByWarrantyExpiry(warrantyExpiry);
        }
        else if (nextMaintenanceDate !=null) {
            inventoryList=inventoryRepository.findByNextMaintenanceDate(nextMaintenanceDate);
        }
        else if (purchaseCost !=null) {
            inventoryList=inventoryRepository.findByPurchaseCost(purchaseCost);
        }
        else if (createdBy !=null) {
            inventoryList=inventoryRepository.findByCreatedBy(createdBy);
        }
        else if (installedSoftwares !=null) {
            inventoryList=inventoryRepository.findByInstalledSoftwares(installedSoftwares);
        }

        else {
            inventoryList = inventoryRepository.findAll();
        }
        if (inventoryList.isEmpty()) {
            throw new ResourceNotFoundException("No inventory found");
        }

        return inventoryList;
    }



    @GetMapping("{id}")
    public ResponseEntity<Inventory> getInventorybyId(@PathVariable UUID id) {
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory not found with id " + id));
        return ResponseEntity.ok(inventory);
    }

>>>>>>> origin/main
    @PutMapping("{id}")
    public ResponseEntity<Inventory> updateInventory(@PathVariable UUID id, @RequestBody Inventory inventoryDetails) {

        Inventory updateInventory = inventoryRepository.findById(id).orElseThrow(
                () -> new ResolutionException("Inventory not found with id " + id)
        );


        if(inventoryDetails.getName() !=null)
        {
            updateInventory.setName(inventoryDetails.getName());
        }
        if(inventoryDetails.getSerialNum() != null)
        {
            updateInventory.setSerialNum(inventoryDetails.getSerialNum());
        }
        if(inventoryDetails.getManufacturer() != null)
        {
            updateInventory.setManufacturer(inventoryDetails.getManufacturer());
        }
        if(inventoryDetails.getSerialNum() != null)
        {
            updateInventory.setModel(inventoryDetails.getModel());
        }
        if(inventoryDetails.getSerialNum() != null)
        {
            updateInventory.setProcessor(inventoryDetails.getProcessor());
        }
        if(inventoryDetails.getSerialNum() != null)
        {
            updateInventory.setMemoryType(inventoryDetails.getMemoryType());
        }
        if(inventoryDetails.getMemorySize() !=null)
        {
            updateInventory.setMemorySize(inventoryDetails.getMemorySize());
        }
        if(inventoryDetails.getStorageType() !=null)
        {
            updateInventory.setStorageType(inventoryDetails.getStorageType());
        }
        if(inventoryDetails.getStorageSize() !=null)
        {
            updateInventory.setStorageSize(inventoryDetails.getStorageSize());
        }
        if(inventoryDetails.getOperatingSystem() !=null)
        {
            updateInventory.setOperatingSystem(inventoryDetails.getOperatingSystem());

        }
        if(inventoryDetails.getPurchaseDate() !=null)
        {
            updateInventory.setPurchaseDate(inventoryDetails.getPurchaseDate());

        }
        if(inventoryDetails.getPurchaseCost() !=null)
        {
            updateInventory.setPurchaseCost(inventoryDetails.getPurchaseCost());

        }
        if(inventoryDetails.getWarrantyExpiry() !=null){
            updateInventory.setWarrantyExpiry(inventoryDetails.getWarrantyExpiry());
        }
        if(inventoryDetails.getShortNote() !=null){
            updateInventory.setShortNote(inventoryDetails.getShortNote());

        }
        if(inventoryDetails.getLastMaintenanceDate() !=null){
            updateInventory.setLastMaintenanceDate(inventoryDetails.getLastMaintenanceDate());

        }
        if(inventoryDetails.getNextMaintenanceDate() !=null){
            updateInventory.setNextMaintenanceDate(inventoryDetails.getNextMaintenanceDate());

        }
        if(inventoryDetails.getCreatedAt() !=null){
            updateInventory.setCreatedAt(inventoryDetails.getCreatedAt());

        }
        if (inventoryDetails.getUpdatedAt() !=null)
        {
            updateInventory.setUpdatedAt(inventoryDetails.getUpdatedAt());

        }
        if(inventoryDetails.getCreatedAt() !=null)
        {
            updateInventory.setCreatedBy(inventoryDetails.getCreatedBy());

        }
        if(inventoryDetails.getInstalledSoftwares() !=null)
        {
            updateInventory.setInstalledSoftwares(inventoryDetails.getInstalledSoftwares());

        }

        inventoryRepository.save(updateInventory);

        return ResponseEntity.ok(updateInventory);
    }

<<<<<<< HEAD

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteInventory(@PathVariable UUID id) {
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventory not found with id " + id));
        inventoryRepository.delete(inventory);
        return ResponseEntity.ok("Inventory id " + id + "has been deleted");
    }

    @GetMapping("{id}")
    public ResponseEntity<Inventory> getInventoryDetails(@PathVariable UUID id) {
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventory not found with id " + id));
        return ResponseEntity.ok(inventory);
    }

}
=======
}
>>>>>>> origin/main

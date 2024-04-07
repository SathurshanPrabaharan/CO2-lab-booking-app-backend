package com.inventoryservice.Controllers;

import com.inventoryservice.DTO.InventoryRequest;
import com.inventoryservice.Enums.STATUS;
import com.inventoryservice.Exception.InventoryNotFoundException;
import com.inventoryservice.Models.Inventory;
import com.inventoryservice.Repositories.InventoryRepository;
import com.inventoryservice.Response.InventoryResponse;
import com.inventoryservice.Services.InventoryService;

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

    @Autowired
    private InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        super();
        this.inventoryService = inventoryService;
    }

    //create
    @PostMapping
    public ResponseEntity<Object> saveInventory(@RequestBody InventoryRequest result){
        Inventory savedInventory = inventoryService.saveInventory(result);
        String message = "Inventory  posted successfully";
        InventoryResponse response = new InventoryResponse(savedInventory, message);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Object> getAllInventory(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate lastMaintenanceDate,
                                           @RequestParam(required = false) String name,
                                                  @RequestParam(required = false) String serialNum,
                                           @RequestParam(required = false) String manufacturer,
                                           @RequestParam(required = false) String model,
                                           @RequestParam(required = false) String processor,
                                           @RequestParam(required = false) String memoryType,
                                           @RequestParam(required = false) String memorySize,
                                           @RequestParam(required = false) String storageType,
                                           @RequestParam(required = false) String storageSize,
                                           @RequestParam(required = false) String operatingSystem,
                                           @RequestParam(required = false) STATUS status,
                                           @RequestParam(required = false) @DateTimeFormat (iso = DateTimeFormat.ISO.DATE) LocalDate purchaseDate,
                                           @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate warrantyExpiry,
                                           @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate nextMaintenanceDate,
                                           @RequestParam(required = false) Float purchaseCost,
                                           @RequestParam(required = false) Long createdBy,
                                           @RequestParam(required = false) List<Integer>installedSoftwares) throws InventoryNotFoundException {
        List<Inventory> inventoryList;
        if (lastMaintenanceDate != null) {
            inventoryList = inventoryRepository.findByLastMaintenanceDate(lastMaintenanceDate);
        }
        else if (serialNum != null) {
            inventoryList = inventoryRepository.findBySerialNum(serialNum);
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
            throw new InventoryNotFoundException("No inventory found");
        }

        return  ResponseEntity.ok(inventoryList);
    }


   @GetMapping("{id}")
    public ResponseEntity<Inventory> getInventorybyId(@PathVariable UUID id) throws InventoryNotFoundException {
            return ResponseEntity.ok(inventoryService.getInventory(id));
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> updateInventory(@PathVariable UUID id ,@RequestBody Inventory inventoryDetails) throws InventoryNotFoundException {
        Inventory updateInventory  = inventoryService.updateInventory(id,inventoryDetails);
        String message = "Inventory  updated successfully";
        InventoryResponse response = new InventoryResponse(updateInventory,message);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

}
